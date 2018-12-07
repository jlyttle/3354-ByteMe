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
    const title = req.body.title;
    const desc = req.body.desc;
    const startDay = req.body.startDay;
    const endDay = req.body.endDay;
    const startMin = req.body.startMin;
    const startHour = req.body.startHour;
    const endMin = req.body.endMin;
    const endHour = req.body.endHour;

    const receivingPhoneNumber = req.body.phoneNum;
    const url = 'http://calender.com/share/?' +title +'/' +desc +'/' + startDay +
    '/' + endDay +'/' + startHour + ':' + startMin +'/'+endHour + ':' + endMin;

    console.log(url);
   
    client.messages
        .create({
            body: url,
            from: '+19562757975',
            to: receivingPhoneNumber
        })
        .then(message => console.log(message.sid))
        .done();
});


const port = process.env.PORT || 5000;
app.listen(port, () => console.log(`server running on port ${port}`));
