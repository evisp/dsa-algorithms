from dataclasses import dataclass
from graph.city import City


@dataclass
class Road:
    from_city: City
    to_city: City
    distance_km: int
    bidirectional: bool

    def to_dict(self):
        return {
            "from": self.from_city.name,
            "to": self.to_city.name,
            "distance_km": self.distance_km,
            "bidirectional": self.bidirectional
        }

    def __repr__(self):
        direction = "↔" if self.bidirectional else "→"
        return f"{self.from_city.name} {direction} {self.to_city.name} ({self.distance_km}km)"