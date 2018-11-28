const express = require("express");
const app = express();
const accountSid = 'AC7114a1c8bac24c7a8add4c34d35887d6';
const authToken = 'a867af19b80bdc646a686406bb441081';
const client = require('twilio')(accountSid, authToken);
const bodyParser = require("body-parser");
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());



app.get("/", (req, res) => res.send("Hello"));

app.post("/send", (req, res) => {
    const msg = req.body.msg;
    const receivingPhoneNumber = req.body.phoneNum;
    console.log(msg + ' ' + receivingPhoneNumber);
   
    client.messages
        .create({
            body: msg,
            from: '+19562757975',
            to: receivingPhoneNumber
        })
        .then(message => console.log(message.sid))
        .done();
});


const port = process.env.PORT || 5000;
app.listen(port, () => console.log(`server running on port ${port}`));
