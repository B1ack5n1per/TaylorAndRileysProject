const colors = require('./Enums.js').colors;
const directions = require('./Enums.js').directions;

class Player {
  constructor(players, spawns) {
    this.id = 0;
    this.x = 0;
    this.y = 0;
    this.ready = false;
    this.confirmed = false;
    this.dir = directions[0];
    let spawn;
    let freeColor = 'black';
    

    for (let i = 0; i < spawns.length; i++) {
      let valid = true;
        for (let j = 0; j < players.length; j++) {
            if (players[j].x == spawns[i].x && players[j]. y == spawns[i].y) {
                valid = false;
                break;
            }
        }
      if (valid) {
        spawn = spawns[i];
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