const express = require('express');
const bodyParser = require('body-parser');
const port = process.env.port || 3000;
const app = express();
const colors = require('./Enums.js').colors;
const Player = require('./Player.js');

let messages = [];
let maps = [[], [], []];

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
  if (maps[req.body.map].length < colors.length) {
    let player = new Player(maps[req.body.map], req.body.spawns);
    player.username = req.body.username;
    maps[req.body.map].push(player);
    res.send(maps[req.body.map][maps[req.body.map].length - 1]);
    return;
  }
  res.send('');
});

app.post('/ready', (req, res) => {
  for (let i = 0; i < maps[req.body.map].length; i++) {
    if (maps[req.body.map][i].id == req.body.player.id) {
      maps[req.body.map][i].x = req.body.player.x;
      maps[req.body.map][i].y = req.body.player.y;
      maps[req.body.map][i].dir = req.body.player.dir;
      maps[req.body.map][i].ready = true;
      maps[req.body.map][i].turns = req.body.turns;
      maps[req.body.map][i].xi = req.body.player.xi;
      maps[req.body.map][i].yi = req.body.player.yi;
      if (maps[req.body.map][i].alive) maps[req.body.map][i].alive = req.body.player.alive;
    }
  }
  delay(1000, res, req.body.map, req.body.player);
});

app.post('/leave', (req, res) => {
  for (let i = 0; i < maps[req.body.map].length; i++) {
    if (maps[req.body.map][i].id == req.body.player.id) {
      maps[req.body.map].splice(i, 1);
    }
  }
  res.send();
});

app.post('/message', (req, res) => {
  if (req.body.name.length > 0 && req.body.msg.length > 0) messages.push(req.body);
  res.send();
});

app.get('/reset', (req, res) => {
    maps[req.body.map] = [];
    res.send('reset successful');
});

app.post('/kill', (req, res) => {
  console.log(req.body);
  for (let i = 0; i < maps[req.body.map].length; i++) {
    if (maps[req.body.map][i].id == req.body.id) {
      maps[req.body.map][i].alive = false;
      maps[req.body.map][i].xi = maps[req.body.map][i].x;
      maps[req.body.map][i].yi = maps[req.body.map][i].y;
      console.log('operation successful');
    }
  }
  res.send();
});


function delay(time, res, map, player) {
  let allReady = true;
  for (let i = 0; i < maps[map].length; i++) {
    if (!maps[map][i].ready && maps[map][i].alive) allReady = false;
  }
  if (!allReady) {
    setTimeout(() => {
      delay(time, res, map, player);
    }, time);
  } else {
    let allSent = true;
    for (let i = 0; i < maps[map].length; i++) {
      if (maps[map][i].id == player.id) maps[map][i].sent = true;
      if (!maps[map][i].sent) allSent = false;
    }
    if (allSent) {
      for (let i = 0; i < maps[map].length; i++) {
        maps[map][i].ready = false;
        maps[map][i].sent = false;
      }
    }
    res.send({ players: maps[map] });
  }
}
