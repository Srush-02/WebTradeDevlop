import React, { useState, useEffect } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import AmendModal from "./AmendModal";
import { format } from "date-fns";
import axios from "axios";

const TradeBlotter = ({ refresh, blotterData, setBlotterData }) => {
  const [fromDate, setFromDate] = useState(null);
  const [toDate, setToDate] = useState(null);
  const [amendTradeData, setAmendTradeData] = useState(null);
  const [filteredTrades, setFilteredTrades] = useState([]);

  // Filter trades based on date range
//   useEffect(() => {
//     if (!blotterData) return;

//     console.log({
  
//   fromDate,
//   toDate
// });
    
//     if (fromDate && toDate) {
//       const start = new Date(fromDate);
//     start.setHours(0, 0, 0, 0); // start of day

//     const end = new Date(toDate);
//     end.setHours(23, 59, 59, 999);

//       const filtered = blotterData.filter(trade => {
//       const tradeDate = trade.createdTimestamp
//         ? new Date(Number(trade.createdTimestamp))
//         : trade.created_ts
//           ? new Date(trade.created_ts)
//           : null;

//       if (!tradeDate) return false;

//       return tradeDate >= start && tradeDate <= end;
//     });

//     setFilteredTrades(filtered);
//     } else {
//       setFilteredTrades(blotterData);
//     }
//   }, [fromDate, toDate, blotterData]);

useEffect(() => {
  refreshBlotterData();
}, [fromDate, toDate]);

const TOKEN = localStorage.getItem("token");

  // Refresh blotter data
  const refreshBlotterData = () => {
    axios
      .get("http://localhost:9091/trade-monitor", {
        params: {
          account: "LME10099, LHOUSE",
          FD: fromDate ? format(fromDate, "dd/MM/yyyy") : "01/01/2025",
          TD: toDate ? format(toDate, "dd/MM/yyyy") : format(new Date(), "dd/MM/yyyy"),

        },
        headers: {
          Authorization: `Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc2NjM3MzExMSwiZXhwIjoxNzY2Mzc2NzExfQ.2gl-R31EmS-r8xMCDl7YPdZJrtxgr_nQs9XpWPuIYs8`,
        },
      })

      .then((response) => {
        setBlotterData(response.data.dataObject);
        console.log("Blotter data refreshed");
      })
      .catch((err) => {
        console.error("Error refreshing blotter:", err);
      });
  
    }
  // Handle Amend button click
  const handleAmendClick = (trade) => {
    console.log("Amending trade:", trade);
    setAmendTradeData(trade);
  };

  // Handle Amend modal close
  const handleAmendClose = () => setAmendTradeData(null);

  // Handle Amend submission
  const handleAmendSubmit = async (updatedFields) => {
    try {

      const payload = {
      traderUUID: amendTradeData.traderUUID,
      metal: updatedFields.metal,
      buyer: amendTradeData.buyer,
      seller: amendTradeData.seller,
      comment: updatedFields.comment || "",
      lots: updatedFields.lots,
      createdTimestamp: amendTradeData.createdTimestamp,
      price: updatedFields.price,
      tradeType: "MODIFY",
      rowNumber: amendTradeData.rowNumber,
      lastModifiedBy: "Wai",
      lastModifiedTimestamp: new Date().toISOString(),
      createdBy: amendTradeData.createdBy
    };
      console.log("Submitting amend:", updatedFields);
      
      // Call your backend API to update the trade
      const response = await axios.post(
        `http://localhost:9091/save`,
        [payload],
        {
          headers: {
            Authorization: `Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc2NjM3MzExMSwiZXhwIjoxNzY2Mzc2NzExfQ.2gl-R31EmS-r8xMCDl7YPdZJrtxgr_nQs9XpWPuIYs8`,
          }
        }
      );

       setAmendTradeData(null);
       setFilteredTrades([]); 
refreshBlotterData();
    alert("Trade amended successfully!");
  } catch (error) {
    console.error(error.response?.data || error);
    alert("Failed to amend trade");
  }
};
      

  // Handle Cancel
  const handleCancel = async (trade) => {
    if (!window.confirm("Are you sure you want to cancel this trade?")) return;

    try {
      // Call backend API to cancel
      const response = await axios.post(
        `http://localhost:9091/save`,
        { tradeType: "CANCEL" },
        {
          headers: {
            Authorization: `Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc2NjM3MzExMSwiZXhwIjoxNzY2Mzc2NzExfQ.2gl-R31EmS-r8xMCDl7YPdZJrtxgr_nQs9XpWPuIYs8`,
          }
        }
      );

      if (response.status === 200) {
        // Update local state
        const updatedTrades = blotterData.map(t =>
          (t.traderUUID === trade.traderUUID || t.id === trade.id)
            ? { ...t, tradeType: "CANCEL" }
            : t
        );
        
        setBlotterData(updatedTrades);
        
        // Refresh data
        refreshBlotterData();
        
        alert("Trade cancelled successfully!");
      }
    } catch (error) {
      console.error("Error cancelling trade:", error);
      alert("Failed to cancel trade. Check console for details.");
    }
  };

  // Determine which data to display
 //onst displayData = filteredTrades.length > 0 ? filteredTrades : blotterData || [];
const displayData = blotterData || [];

  return (
    <div style={{ padding: "30px", fontFamily: "Arial, sans-serif" }}>
      <h2 style={{ borderBottom: "2px solid #eee", paddingBottom: "10px" }}>Trade Blotter</h2>

      <div style={{ marginBottom: "15px", display: "flex", gap: "10px", alignItems: "center" }}>
        <div>
          <label>From Date: </label>
          <DatePicker 
            selected={fromDate} 
            onChange={setFromDate} 
            dateFormat="yyyy-MM-dd" 
            placeholderText="Select start date" 
          />
        </div>
        <div>
          <label>To Date: </label>
          <DatePicker 
            selected={toDate} 
            onChange={setToDate} 
            dateFormat="yyyy-MM-dd" 
            placeholderText="Select end date" 
            minDate={fromDate}
          />
        </div>
        <button onClick={() => { setFromDate(null); setToDate(null); }}>Clear</button>
        <button onClick={refreshBlotterData}>Refresh</button>
      </div>

      <table border="1" cellPadding="8" style={{ width: "100%", borderCollapse: "collapse", marginTop: "20px" }}>
        <thead>
          <tr style={{ backgroundColor: "#f2f2f2" }}>
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
          {displayData.map((trade, index) => (
            <tr key={`${trade.traderUUID}-${trade.rowNumber}`}>
              <td>{trade.traderUUID || trade.id || "N/A"}</td>
              <td>{trade.buyer}</td>
              <td>{trade.seller}</td>
              <td>{trade.metal}</td>
              <td>{trade.lots}</td>
              <td>{trade.price}</td>
              <td>
                <span style={{
                  padding: "4px 8px",
                  borderRadius: "4px",
                  fontWeight: "bold",
                  backgroundColor: trade.tradeType === "CANCEL" ? "#f8d7da" :
                                 trade.tradeType === "MODIFY" ? "#d1ecf1" : "#d4edda",
                  color: trade.tradeType === "CANCEL" ? "#721c24" :
                        trade.tradeType === "MODIFY" ? "#0c5460" : "#155724"
                }}>
                  {trade.tradeType || "ACTIVE"}
                </span>
              </td>
              <td>
                {format(
                  trade.createdTimestamp 
                    ? new Date(Number(trade.createdTimestamp))
                    : trade.created_ts 
                      ? new Date(trade.created_ts)
                      : new Date(),
                  "dd/MM/yyyy"
                )}
              </td>
              <td>
                {trade.tradeType !== "CANCEL" && (
                  <>
                    <button 
                      onClick={() => handleAmendClick(trade)} 
                      style={{ 
                        backgroundColor: "#28a745", 
                        color: "white", 
                        border: "none", 
                        padding: "6px 12px", 
                        borderRadius: "4px", 
                        marginRight: "5px", 
                        cursor: "pointer" 
                      }}
                    >
                      Amend
                    </button>
                    <button 
                      onClick={() => handleCancel(trade)} 
                      style={{ 
                        backgroundColor: "#dc3545", 
                        color: "white", 
                        border: "none", 
                        padding: "6px 12px", 
                        borderRadius: "4px", 
                        cursor: "pointer" 
                      }}
                    >
                      Cancel
                    </button>
                  </>
                )}
                {trade.tradeType === "CANCEL" && (
                  <span style={{ color: "#6c757d" }}>Cancelled</span>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {displayData.length === 0 && (
        <div style={{ textAlign: "center", padding: "40px", color: "#6c757d" }}>
          No trades found
        </div>
      )}

      {amendTradeData && (
        <AmendModal 
          trade={amendTradeData} 
          onClose={handleAmendClose} 
          onSubmit={handleAmendSubmit} 
        />
      )}
    </div>
  );
};

export default TradeBlotter;