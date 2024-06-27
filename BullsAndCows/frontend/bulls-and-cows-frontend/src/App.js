import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Dashboard from './components/Dashboard';
import GamePage from './components/GamePage';

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Dashboard />} />
                <Route path="/game/:id" element={<GamePage />} />
            </Routes>
        </Router>
    );
}

export default App;
