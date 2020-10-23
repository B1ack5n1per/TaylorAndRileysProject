const express = require('express');
const bodyParser = require('body-parser');
const port = process.env.port || 3000;
const app = express();
const Player = require('./Player.js');

let messages = [];
let players = [];

app.listen(port, console.log(`server started on port ${port}`));

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }))

app.get('/', (req, res) => {
  res.send('server running');
});

app.get('/messages', (req, res) => {
  res.send(messages);
});

app.post('/join', (req, res) => {
  let player = new Player(players, req.body);
  players.push(player);
  console.log(players);
  setTimeout(() => {
    if (confirmPlayer(player.id)) removePlayer(player.id);
    else console.log('player validated');
  }, 60000);
  res.send(players[players.length - 1]);
});

app.post('/move', (req, res) => {
  setTimeout(() => {
    console.log('clicked');
    res.send(players)
  }, 1000);
});

app.post('/leave', (req, res) => {
  removePlayer(req.body.id);
  res.send();
});

app.post('/message', (req, res) => {
  if (req.body.name.length > 0 && req.body.msg.length > 0) messages.push(req.body);
  res.send();
});

app.get('/confirm', (req, res) => {
  for (let i = 0; i < players.length; i++) {
    if (players[i].id = req.body.id) {
      players[i].confirmed = true;
      console.log(players);
    }
  }
  res.send();
});

function removePlayer(id) {
  for (let i = 0; i < players.length; i++) if (players[i].id == id) players.splice(i, 1);
  console.log(players);
}
function confirmPlayer(id) {
  for (let i = 0; i < players.length; i++) if (players[i].id == id) return players[i].confirmed;
}