import React, { useState } from 'react';
import { saveTrade } from '../api/tradeService';
import './TradeForm.css';
import axios from 'axios';


const METAL_OPTIONS = ["1AHD","AHD", "CAD", "1CAD", "1PBD", "PBD"];

 
 
const TradeCaptureForm = ({ onTradeSaved, setBlotterData}) => {
  const [formData, setFormData] = useState({
    buyer: '',
    seller: '',
    metal: '',
    lots: 0,
    price: 0.0,
    comment: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;

    let formattedValue = value;
    if (name === 'lots') {
      formattedValue = value === '' ? '' : parseInt(value, 10);
    } else if (name === 'price') {
      formattedValue = value === '' ? '' : parseFloat(value);
    }

    setFormData(prev => ({
      ...prev,
      [name]: formattedValue
    }));

    setErrors(prev => ({
      ...prev,
      [name]: ''
    }));
  };


  const validate = () => {
    const newErrors = {};

    if (!formData.buyer.trim()) newErrors.buyer = "Buyer is required";
    if (!formData.seller.trim()) newErrors.seller = "Seller is required";
    if (!formData.metal) newErrors.metal = "Metal is required";
    if (formData.lots === '' || formData.lots <= 0)
      newErrors.lots = "Lots must be greater than 0";
    if (formData.price === '' || formData.price <= 0)
      newErrors.price = "Price must be greater than 0";

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };



  const handleSubmit = async (e) => {
    e.preventDefault();

   try {
      const response = await saveTrade(formData, "sa");

      // Only alert success if response is 2xx
      if (response.status >= 200 && response.status < 300) {
axios
      .get("http://localhost:9091/trade-monitor", {
        params: {
          account: "LME10099, LHOUSE",
          FD: "20/12/2025",
          TD: "30/12/2025",
        },
        headers: {
          Authorization: `Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc2NjM3MzExMSwiZXhwIjoxNzY2Mzc2NzExfQ.2gl-R31EmS-r8xMCDl7YPdZJrtxgr_nQs9XpWPuIYs8`,
        },
      })
      .then((response) => {
        setBlotterData(response.data.dataObject);

        console.log("reposne--->", response);
      })
      .catch((err) => {

        console.error(err);
      });
          } else {
        alert("Unexpected response status: " + response.status);
      }

      setFormData({
        buyer: '',
        seller: '',
        metal: '',
        lots: 0,
        price: 0.0,
        comment: ''
      });
      
      onTradeSaved();
    } catch (error) {
      console.error("Full Error Object:", error);
      const errorMsg = error.response?.data?.message || "Check Spring Boot Console for details!";
      alert("Error: " + errorMsg);
    }
  };
 
const [errors, setErrors] = useState({});
  const [loading, setLoading] = useState(false);

  return (
    <div className="trade-container">
      <h2 className="trade-title">Trade Capture</h2>

      <form className="trade-form" onSubmit={handleSubmit}>

       
        <input
          className="trade-input"
          name="buyer"
          placeholder="Buyer Name"
          value={formData.buyer}
          onChange={handleChange}
        />
        {errors.buyer && <span className="trade-error">{errors.buyer}</span>}

       
        <input
          className="trade-input"
          name="seller"
          placeholder="Seller Name"
          value={formData.seller}
          onChange={handleChange}
        />
        {errors.seller && <span className="trade-error">{errors.seller}</span>}

        
        <select
          className="trade-select"
          name="metal"
          value={formData.metal}
          onChange={handleChange}
        >
          {METAL_OPTIONS.map(m => (
            <option key={m} value={m}>{m}</option>
          ))}
        </select>
        {errors.metal && <span className="trade-error">{errors.metal}</span>}

      
        <div className="trade-row">
          <div className="trade-col">
            <input
              className="trade-input"
              name="lots"
              type="number"
              placeholder="Lots"
              value={formData.lots}
              onChange={handleChange}
            />
            {errors.lots && <span className="trade-error">{errors.lots}</span>}
          </div>

          <div className="trade-col">
            <input
              className="trade-input"
              name="price"
              type="number"
              step="0.01"
              placeholder="Price"
              value={formData.price}
              onChange={handleChange}
            />
            {errors.price && <span className="trade-error">{errors.price}</span>}
          </div>
        </div>

        <textarea
          className="trade-textarea"
          name="comment"
          placeholder="Trade Comments"
          value={formData.comment}
          onChange={handleChange}
        />

        <button
          className="trade-btn"
          type="submit"
          disabled={loading}
        >
          {loading ? "Saving..." : "Save"}
        </button>

      </form>
    </div>
  );
};

export default TradeCaptureForm;