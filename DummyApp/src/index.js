const path = require('path')
const ps = require('./pathSmoother');
var cors = require('cors');
let arguments = process.argv.splice(2);

const express = require('express');
const app = express();

app.use(express.json()) // for parsing application/json
app.use(express.urlencoded({ extended: true })) // for parsing application/x-www-form-urlencoded
app.use(cors());


const port = arguments[0] || 8080;

app.get('/', (req, res) => {
    res.setHeader("Access-Control-Allow-Origin","*");
    res.setHeader('Access-Control-Allow-Methods', 'POST, GET, OPTIONS');
    res.setHeader('Access-Control-Allow-Headers', '*');
    res.sendFile(path.join(__dirname, 'index.html'))
});


app.post('/median', (req, res, next) => {
    console.log(req.headers.cookie);
    let data = req.body.data;
    let filterGranularity = req.body.filterGranularity;

    res.cookie('TrackingCookie', 'TrackingCookieValue')
    res.send(ps.median(data, filterGranularity));
});


app.post('/mean', (req, res, next) => {
    console.log('Oi')
    console.log("Cookies: " + req.headers.cookie);
    let data = req.body.data;
    console.log(data);
    let filterGranularity = req.body.filterGranularity;

    res.cookie('TrackingCookie', 'TrackingCookieValue')
    res.send(ps.mean(data, filterGranularity));
});

app.listen(port, () => console.log(`Example app listening on port ${port}!`))
