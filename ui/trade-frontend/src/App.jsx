import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import './App.css';

// Import components
import Login from './components/Login';
import HomePAge from './components/HomePAge';

import TradeCaptureForm from './components/TradeCaptureForm';
import TradeBlotter from './components/TradeBlotter';


// Create theme
const theme = createTheme({
  palette: {
    mode: 'light',
    primary: {
      main: '#1976d2',
    },
    secondary: {
      main: '#dc004e',
    },
  },
});

function App() {
  const [refresh, setRefresh] = useState(false);
    const [blotterData, setBlotterData] = useState([]);

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Router>
        <div className="App">
          <Routes>
            {/* Public Routes */}
            <Route path="/login" element={<Login />} />
            <Route path="/" element={<HomePAge />} />
            

            

            
            <Route path="/capture" element={
              
                <div style={{ padding: "2rem" }}>
                  {/* <TradeCaptureForm onTradeSaved={() => setRefresh(!refresh)} /> */}
                              <TradeCaptureForm setBlotterData={setBlotterData} onTradeSaved={() => setRefresh(!refresh)} />
                  <TradeBlotter key={refresh} blotterData={blotterData} setBlotterData={setBlotterData} />
                </div>
              
            } />
          
            
            {/* Default redirect */}
            {/* <Route path="/" element={<Navigate to="/login" replace />} /> */}
            
            {/* 404 route */}
            {/* <Route path="*" element={<Navigate to="/login" replace />} /> */}
          </Routes>
        </div>
      </Router>
    </ThemeProvider>
  );
}

export default App;