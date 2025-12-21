


//----------------For test--------



import React, { useState, useEffect } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import AmendModal from "./AmendModal";

// Mock trades
const MOCK_TRADES = [
  { traderUUID: "uuid-1", buyer: "BankA", seller: "BankB", metal: "Gold", lots: 10, price: 2350, status: "NEW", created_ts: "2025-12-19T10:30:00" },
  { traderUUID: "uuid-2", buyer: "BankC", seller: "BankD", metal: "Silver", lots: 5, price: 28, status: "NEW", created_ts: "2025-12-20T12:00:00" },
  { traderUUID: "uuid-3", buyer: "BankE", seller: "BankF", metal: "Platinum", lots: 2, price: 1050, status: "AMENDED", created_ts: "2025-12-20T14:15:00" }
];

const TradeBlotter = () => {
  const [trades, setTrades] = useState([]);
  const [filteredTrades, setFilteredTrades] = useState([]);
  const [fromDate, setFromDate] = useState(null);
  const [toDate, setToDate] = useState(null);
  const [amendTradeData, setAmendTradeData] = useState(null);

  useEffect(() => {
    setTrades(MOCK_TRADES);
    setFilteredTrades(MOCK_TRADES);
  }, []);

  useEffect(() => {
    if (fromDate && toDate) {
      const filtered = trades.filter(trade => {
        const t = new Date(trade.created_ts).getTime();
        return t >= fromDate.getTime() && t <= toDate.getTime();
      });
      setFilteredTrades(filtered);
    } else {
      setFilteredTrades(trades);
    }
  }, [fromDate, toDate, trades]);

  // Mock Amend
  const handleAmendClick = (trade) => setAmendTradeData(trade);
  const handleAmendClose = () => setAmendTradeData(null);
  const handleAmendSubmit = (updatedFields) => {
    const updatedTrades = trades.map(t =>
      t.traderUUID === amendTradeData.traderUUID
        ? { ...t, ...updatedFields, status: "AMENDED" }
        : t
    );
    setTrades(updatedTrades);
    setAmendTradeData(null);
    alert("Trade amended successfully (mock)!");
  };

  // Mock Cancel
  const handleCancel = (trade) => {
    if (!window.confirm("Are you sure you want to cancel this trade?")) return;

    const updatedTrades = trades.map(t =>
      t.traderUUID === trade.traderUUID
        ? { ...t, status: "CANCELLED" }
        : t
    );
    setTrades(updatedTrades);
    alert("Trade cancelled successfully (mock)!");
  };

  return (
    <div style={{ padding: "30px", fontFamily: "Arial, sans-serif" }}>
      <h2 style={{ borderBottom: "2px solid #eee", paddingBottom: "10px" }}>Trade Blotter</h2>

      <div style={{ marginBottom: "15px", display: "flex", gap: "10px" }}>
        <div>
          <label>From Date: </label>
          <DatePicker selected={fromDate} onChange={setFromDate} dateFormat="yyyy-MM-dd" placeholderText="Select start date" />
        </div>
        <div>
          <label>To Date: </label>
          <DatePicker selected={toDate} onChange={setToDate} dateFormat="yyyy-MM-dd" placeholderText="Select end date" />
        </div>
        <button onClick={() => { setFromDate(null); setToDate(null); }}>Clear</button>
      </div>

      <table border="1" cellPadding="5" style={{ width: "100%", borderCollapse: "collapse" }}>
        <thead>
          <tr>
            <th>ID</th>
            <th>Buyer</th>
            <th>Seller</th>
            <th>Metal</th>
            <th>Lots</th>
            <th>Price</th>
            <th>Status</th>
            <th>Date</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {filteredTrades.map(trade => (
            <tr key={trade.traderUUID}>
              <td>{trade.traderUUID}</td>
              <td>{trade.buyer}</td>
              <td>{trade.seller}</td>
              <td>{trade.metal}</td>
              <td>{trade.lots}</td>
              <td>{trade.price}</td>
              <td>{trade.status}</td>
              <td>{new Date(trade.created_ts).toLocaleDateString()}</td>
              <td>
                <button onClick={() => handleAmendClick(trade)} style={{ backgroundColor: "#28a745", color: "white", border: "none", padding: "5px 10px", borderRadius: "4px", marginRight: "5px", cursor: "pointer" }}>Amend</button>
                <button onClick={() => handleCancel(trade)} style={{ backgroundColor: "#dc3545", color: "white", border: "none", padding: "5px 10px", borderRadius: "4px", cursor: "pointer" }}>Cancel</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {amendTradeData && <AmendModal trade={amendTradeData} onClose={handleAmendClose} onSubmit={handleAmendSubmit} />}
    </div>
  );
};

export default TradeBlotter;
