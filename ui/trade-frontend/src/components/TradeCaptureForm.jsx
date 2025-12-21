



import React, { useState } from 'react';
import { saveTrade } from '../api/tradeService';

const METAL_OPTIONS = ["Gold", "Silver", "Platinum", "Palladium"];

const TradeCaptureForm = ({ onTradeSaved }) => {
  const [formData, setFormData] = useState({
    buyer: '',
    seller: '',
    metal: 'Gold',
    lots: 0,
    price: 0.0,
    comment: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    const formattedValue = (name === 'lots') ? parseInt(value, 10) || 0 
                         : (name === 'price') ? parseFloat(value) || 0.0 
                         : value;
    setFormData({ ...formData, [name]: formattedValue });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await saveTrade(formData, "sa");

      // Only alert success if response is 2xx
      if (response.status >= 200 && response.status < 300) {
        alert("Trade Saved Successfully!");
      } else {
        alert("Unexpected response status: " + response.status);
      }

      setFormData({
        buyer: '',
        seller: '',
        metal: 'Gold',
        lots: 0,
        price: 0.0,
        comment: ''
      });

      onTradeSaved(); // Refresh blotter
    } catch (error) {
      console.error("Full Error Object:", error);
      const errorMsg = error.response?.data?.message || "Check Spring Boot Console for details!";
      alert("Error: " + errorMsg);
    }
  };

  return (
    <div style={{ padding: '30px', fontFamily: 'Arial, sans-serif', maxWidth: '400px' }}>
      <h2 style={{ borderBottom: '2px solid #eee', paddingBottom: '10px' }}>Trade Entry</h2>
      <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '15px' }}>
        <input name="buyer" placeholder="Buyer Name" value={formData.buyer} onChange={handleChange} required style={inputStyle} />
        <input name="seller" placeholder="Seller Name" value={formData.seller} onChange={handleChange} required style={inputStyle} />
        <select name="metal" value={formData.metal} onChange={handleChange} style={inputStyle}>
          {METAL_OPTIONS.map(m => <option key={m} value={m}>{m}</option>)}
        </select>
        <div style={{ display: 'flex', gap: '10px' }}>
          <input name="lots" type="number" placeholder="Lots" value={formData.lots} onChange={handleChange} style={inputStyle} />
          <input name="price" type="number" step="0.01" placeholder="Price" value={formData.price} onChange={handleChange} style={inputStyle} />
        </div>
        <textarea name="comment" placeholder="Trade Comments" value={formData.comment} onChange={handleChange} style={{ ...inputStyle, height: '80px' }} />
        <button type="submit" style={buttonStyle}>Submit to Backend</button>
      </form>
    </div>
  );
};

const inputStyle = { padding: '10px', borderRadius: '4px', border: '1px solid #ccc', fontSize: '14px' };
const buttonStyle = { padding: '12px', background: '#007bff', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer', fontWeight: 'bold' };

export default TradeCaptureForm;
