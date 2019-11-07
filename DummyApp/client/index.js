const path = require('path')
const express = require('express');
const app = express();
let arguments = process.argv.splice(2);
const port = arguments[0] || 8080;

app.get('/', (req, res) => {
    res.setHeader("Access-Control-Allow-Origin","*");
    res.setHeader('Access-Control-Allow-Methods', 'POST, GET, OPTIONS');
    res.setHeader('Access-Control-Allow-Headers', '*');
    res.sendFile(path.join(__dirname, 'index.html'))
});


app.listen(port, () => console.log(`Example app listening on port ${port}!`))
