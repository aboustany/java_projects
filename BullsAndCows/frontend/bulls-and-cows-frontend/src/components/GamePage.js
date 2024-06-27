import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import '../App.css';

const GamePage = () => {
    const { id } = useParams();
    const [game, setGame] = useState(null);
    const [rounds, setRounds] = useState([]);
    const [guess, setGuess] = useState('');
    const navigate = useNavigate();

    const goBack = () => navigate('/');

    useEffect(() => {
        const fetchGameDetails = async () => {
            try {
                const gameResponse = await axios.get(`http://localhost:8080/games/${id}`);
                setGame(gameResponse.data);
                const roundsResponse = await axios.get(`http://localhost:8080/games/rounds/${id}`);
                setRounds(roundsResponse.data);
            } catch (error) {
                console.error('Error fetching game details:', error);
            }
        };

        fetchGameDetails();
    }, [id]);

    const submitGuess = async () => {
        if (guess && game.status !== 'FINISHED') {
            try {
                const response = await axios.post('http://localhost:8080/games/guess', { gameId: id, guess });
                setRounds([...rounds, response.data]);
                setGuess('');
            } catch (error) {
                console.error('Error submitting guess:', error);
            }
        }
    };

    return (
        <div>
            <button onClick={goBack}>Go Back to Dashboard</button>
            <h2>Game ID: {id}</h2>
            {game && <p>Status: {game.status}</p>}
            <input type="text" value={guess} onChange={(e) => setGuess(e.target.value)} placeholder="Enter your guess"
                   disabled={game?.status === 'FINISHED'}/>
            <button onClick={submitGuess} disabled={game?.status === 'FINISHED'}>Submit Guess</button>
            <div>
                <h3>Rounds</h3>
                {Array.isArray(rounds) && rounds.map((round, index) => (
                    <div key={index} className="round">
                        Guess: {round.guess}, Result: {round.result}
                    </div>
                ))}
            </div>
        </div>
    );
};

export default GamePage;
