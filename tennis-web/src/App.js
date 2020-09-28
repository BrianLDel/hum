import React from 'react';
import './App.css';
import Routes from './components/Routes';
import Menu from './components/Menu';
import { BrowserRouter} from 'react-router-dom';

function App() {

  return (
    <BrowserRouter>
      <div className="App">
        <Menu/>
        <Routes/>
      </div>
    </BrowserRouter>
  );
}

export default App;