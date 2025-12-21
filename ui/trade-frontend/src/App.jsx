import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import TradeForm from './components/TradeForm'
import TradeCaptureForm from "./components/TradeCaptureForm";
import TradeBlotter from "./components/TradeBlotter";


function App() {
  const [count, setCount] = useState(0)
    const [refresh, setRefresh] = useState(false);


  return (
    // <div className="App">
    //   <TradeForm />
    // </div>


      <div style={{padding: "2rem"}}>
          <TradeCaptureForm onTradeSaved={() => setRefresh(!refresh)} />
      <TradeBlotter key={refresh} />
    </div>
    
  );
}

export default App
