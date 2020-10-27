const colors = require('./Enums.js').colors;
const directions = require('./Enums.js').directions;

function randomize(arr) {
  let res = [];
  while (arr.length > 0) {
    let index = Math.floor(Math.random() * arr.length);
    res.push(arr[index]);
    arr.splice(index, 1);
  }
  return res;
}

class Player {
  constructor(players, spawns) {
    this.id = 0;
    this.x = 0;
    this.y = 0;
    this.xi = 0;
    this.yi = 0;
    this.ready = false;
    this.sent = false;
    this.dir = directions[0];
    this.actions = [];
    this.alive = true;

    let spawn;
    let freeColor = 'black';

    let randSpawns = randomize(spawns);
    for (let i = 0; i < randSpawns.length; i++) {
      let valid = true;
        for (let j = 0; j < players.length; j++) {
            if (players[j].x == randSpawns[i].x && players[j].y == randSpawns[i].y) {
                valid = false;
                break;
            }
        }
      if (valid) {
        spawn = randSpawns[i];
        break;
      }
    }

    for (let i = 0; i < colors.length; i++) {
      let valid = true;
      for (let j = 0; j < players.length; j++) {
        if (players[j].color == colors[i]) {
          valid = false;
          break;
        }
      }
      if (valid) {
        freeColor = colors[i];
        break;
      }
    }

    this.id = 0;
    if (players.length > 0) {
      this.id = players[players.length - 1].id + 1;
    }
    if (spawn) {
      this.x = spawn.x;
      this.y = spawn.y;
      this.dir = spawn.dir;
    }
    this.color = freeColor;
  }
}

module.exports = Player;
