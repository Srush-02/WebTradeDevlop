

//=================================New=====================

import axios from 'axios';

export const saveTrade = async (formData, username) => {
  

  const payload =
   [{
  "traderUUID": "...",
  "metal": "...",
  "buyer": "...",
  "seller": "...",
  "comment": "...",
  "lots": 0,
  "createdTimestamp": "...",
  "price": 0,
  "tradeType": "",
  "rowNumber": 0,
  "lastModifiedBy": "...",
  "lastModifiedTimestamp": "...",
  "createdBy": "..."



  }];

  return await axios.post('http://localhost:9091/save', payload, {
    headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc2NjM3MzExMSwiZXhwIjoxNzY2Mzc2NzExfQ.2gl-R31EmS-r8xMCDl7YPdZJrtxgr_nQs9XpWPuIYs8'
    }
  });
};

export const getTrades = async () => {
  const response = await axios.get('http://localhost:9091/getAll');
  return response.data;
};

export const amendTrade = async (id, trade) => {
  const payload = [{ ...trade }]; // backend expects array even for amend
  const response = await axios.put(`http://localhost:9091/amend/${id}`, payload, {
    headers: {
      'Content-Type': 'application/json'
    }
  });
  return response.data;
};
