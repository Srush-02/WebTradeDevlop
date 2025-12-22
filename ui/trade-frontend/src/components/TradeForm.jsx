import React, { useState } from 'react';
//  import { saveTrade } from '../api/tradeService';


const METAL_OPTIONS = ["1AHD","AHD", "CAD", "1CAD", "1PBD", "PBD"];

const TradeForm = () => {
  const [formData, setFormData] = useState({
    buyer: 'abciouou',
    seller: 'ghj',
    metal: 'Gold',
    lots: "8897888",
    price: "65.8",
    comment: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    
    // Crucial: Convert numeric strings to actual numbers for Java
    const formattedValue = (name === 'lots') ? parseInt(value, 10) || 0 
                         : (name === 'price') ? parseFloat(value) || 0.0 
                         : value;

    setFormData({ ...formData, [name]: formattedValue });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await saveTrade(formData, "sa");
      console.log("Response:", response.data);
      alert("Trade Saved Successfully!");
    } catch (error) {
      console.error("Full Error Object:", error);
      const errorMsg = error.response?.data?.message || "Check Spring Boot Console for red text!";
      alert("Error 500: " + errorMsg);
    }
  };

  return (
    <div style={{ padding: '30px', fontFamily: 'Arial, sans-serif', maxWidth: '400px' }}>
      <h2 style={{ borderBottom: '2px solid #eee', paddingBottom: '10px' }}>Trade Capture</h2>
      <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '15px' }}>
        <input name="buyer" placeholder="Buyer Name" onChange={handleChange} required style={inputStyle} />
        <input name="seller" placeholder="Seller Name" onChange={handleChange} required style={inputStyle} />
        {/* <input name="metal" placeholder="Metal" value={formData.metal} onChange={handleChange} style={inputStyle} /> */}
        
       <select name="metal" value={formData.metal} onChange={handleChange} style={inputStyle}>
          {METAL_OPTIONS.map(m => <option key={m} value={m}>{m}</option>)}
        </select>




        <div style={{ display: 'flex', gap: '10px' }}>
          <input name="lots"  placeholder="Lots" onChange={handleChange} style={inputStyle} />
          <input name="price" placeholder="Price" onChange={handleChange} style={inputStyle} />
        </div>
        
        <textarea name="comment" placeholder="Trade Comments" onChange={handleChange} style={{ ...inputStyle, height: '80px' }} />
        
        <button type="submit" style={buttonStyle}>Save</button>
      </form>
    </div>
  );
};

// Simple styles
const inputStyle = { padding: '10px', borderRadius: '4px', border: '1px solid #ccc', fontSize: '14px' };
const buttonStyle = { padding: '12px', background: '#007bff', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer', fontWeight: 'bold' };

export default TradeForm;