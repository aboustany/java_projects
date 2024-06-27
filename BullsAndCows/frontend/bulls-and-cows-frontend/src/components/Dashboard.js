// components/Dashboard.js
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';
import '../App.css';


const Dashboard = () => {
    const [games, setGames] = useState([]);
    const navigate = useNavigate(); // Hook to programmatically navigate

    useEffect(() => {
        fetchGames();
    }, []);

    const fetchGames = async () => {
        try {
            const response = await axios.get('http://localhost:8080/games/game');
            setGames(response.data);
        } catch (error) {
            console.error('Error fetching games:', error);
        }
    };

    const createNewGame = async () => {
        try {
            const response = await axios.post('http://localhost:8080/games/begin');
            // Redirect the user to the game page for the new game
            navigate(`/game/${response.data.id}`);
        } catch (error) {
            console.error('Error creating a new game:', error);
        }
    };

    return (
        <div>
            <h2>Dashboard</h2>
            <button onClick={createNewGame}>Create New Game</button>
            <ul>
                {games.map((game) => (
                    <li key={game.id}>
                        <Link to={`/game/${game.id}`}>Game ID: {game.id} - Status: {game.gameStatus}</Link>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default Dashboard;
