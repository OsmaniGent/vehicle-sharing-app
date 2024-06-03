import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { getToken } from '../utils/auth';

const Dashboard = () => {
    const [data, setData] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const token = getToken();
                const response = await axios.get('http://localhost:3001', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                setData(response.data);
            } catch (error) {
                console.error('Error fetching protected data', error);
            }
        };

        fetchData();
    }, []);

    return (
        <div>
            <h1>Dashboard</h1>
            {data ? <p>{data}</p> : <p>Loading...</p>}
        </div>
    );
};

export default Dashboard;
