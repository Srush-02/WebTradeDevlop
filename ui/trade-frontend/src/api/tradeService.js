

//=================================New=====================

import axios from 'axios';

export const saveTrade = async (formData, username) => {
  
  const payload = [{
    ...formData,
    metal: formData.metal || "Gold", 
    status: "NEW",
    tradeMsgType: "NEW",
    traderUUID: crypto.randomUUID(),
    created_ts: new Date().toISOString()
  }];

  return await axios.post('http://localhost:9091/save', payload, {
    headers: {
      'Content-Type': 'application/json',
      'x-username': username
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
