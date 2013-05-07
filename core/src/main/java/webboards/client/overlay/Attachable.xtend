package webboards.client.overlay

import webboards.client.data.ref.CounterId
import webboards.client.games.Position

class Attachable {
	CounterId counterId;
	Position position;
	
	new(CounterId counterId) {
		this.counterId = counterId;
		this.position = null;
	}
	new(Position position){
		this.counterId = null;
		this.position = position;
	}
}