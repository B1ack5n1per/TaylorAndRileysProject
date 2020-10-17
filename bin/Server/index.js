const express = require('express');
const bodyParser = require('body-parser');
const port = process.env.port || 3000;
const app = express();

let messages = [{ name: 'taylor', msg: 'hi' }, { name: 'riley', msg: 'hi' }];

app.listen(port, console.log(`server started on port ${port}`));

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }))

app.get('/get', (req, res) => {
  console.log('request recieved')
  res.send('hello world');
});

app.post('/get', (req, res) => {
  data = JSON.parse(req.body);
  res.send(data.name);
});

app.get('/messages', (req, res) => {
  res.send(messages);
});

app.post('/message', (req, res) => {
  if (req.body.name.length > 0 && req.body.msg.length > 0) {
    messages.push(req.body);
  }
  res.send();
});
