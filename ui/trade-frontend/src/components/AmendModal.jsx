import React, { useState, useEffect } from "react";

const METAL_OPTIONS = ["Gold", "Silver", "Platinum", "Palladium"];

const AmendModal = ({ trade, onClose, onSubmit }) => {
  const [formData, setFormData] = useState({
    lots: 0,
    price: 0.0,
    metal: "Gold",
    comment: ""
  });

  useEffect(() => {
    if (trade) {
      setFormData({
        lots: trade.lots,
        price: trade.price,
        metal: trade.metal,
        comment: trade.comment || ""
      });
    }
  }, [trade]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    const formattedValue =
      name === "lots"
        ? parseInt(value, 10) || 0
        : name === "price"
        ? parseFloat(value) || 0.0
        : value;

    setFormData({ ...formData, [name]: formattedValue });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(formData);
  };

  if (!trade) return null;

  return (
    <div
      style={{
        position: "fixed",
        top: 0,
        left: 0,
        width: "100%",
        height: "100%",
        backgroundColor: "rgba(0,0,0,0.5)",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        zIndex: 1000
      }}
    >
      <div
        style={{
          backgroundColor: "white",
          padding: "20px",
          borderRadius: "8px",
          width: "350px",
          boxShadow: "0 0 10px rgba(0,0,0,0.3)"
        }}
      >
        <h3>Amend Trade</h3>
        <form onSubmit={handleSubmit} style={{ display: "flex", flexDirection: "column", gap: "10px" }}>
          <label>
            Metal:
            <select name="metal" value={formData.metal} onChange={handleChange}>
              {METAL_OPTIONS.map((m) => (
                <option key={m} value={m}>{m}</option>
              ))}
            </select>
          </label>

          <label>
            Lots:
            <input type="number" name="lots" value={formData.lots} onChange={handleChange} />
          </label>

          <label>
            Price:
            <input type="number" name="price" step="0.01" value={formData.price} onChange={handleChange} />
          </label>

          <label>
            Comment:
            <textarea name="comment" value={formData.comment} onChange={handleChange} />
          </label>

          <div style={{ display: "flex", justifyContent: "flex-end", gap: "10px" }}>
            <button type="button" onClick={onClose}>Cancel</button>
            <button type="submit" style={{ backgroundColor: "#28a745", color: "white" }}>Save</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default AmendModal;
