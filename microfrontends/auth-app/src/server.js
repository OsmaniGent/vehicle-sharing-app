const express = require('express');
const jwt = require('jsonwebtoken');
const bodyParser = require('body-parser');
const cors = require('cors');
require('dotenv').config();

const app = express();
const secretKey = 'U2FsdGVkX1+X2gY48S8dQsX9Bl4OYBcSxL1FNjYnzX8=';

app.use(bodyParser.json());
app.use(cors());

app.post('/login', (req, res) => {
    const { username, password } = req.body;

    if (username === 'test' && password === 'password') {
        const token = jwt.sign({ username }, secretKey, { expiresIn: '1h' });
        res.json({ token });
    } else {
        res.status(401).send('Invalid credentials');
    }
});

app.post('/addUser', (req, res) => {
    const { email, username, hashedPassword, role } = req.body;
    const users = [];
    const newUser = { email, username, hashedPassword, role };
    users.push(newUser);

    res.status(201).send('User registered successfully');
});

app.get('/protected', (req, res) => {
    const token = req.headers['authorization'];
    if (!token) {
        return res.status(403).send('A token is required for authentication');
    }
    try {
        const decoded = jwt.verify(token, secretKey);
        res.status(200).send(`Welcome ${decoded.username}`);
    } catch (err) {
        return res.status(401).send('Invalid Token');
    }
});

app.listen(3001, () => {
    console.log('Authentication service is running on port 3001');
});
