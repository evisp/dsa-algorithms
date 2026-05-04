from dataclasses import dataclass


@dataclass
class City:
    name: str
    type: str        # "capital", "port", "town"
    lat: float
    lng: float

    def to_dict(self):
        return {
            "name": self.name,
            "type": self.type,
            "lat": self.lat,
            "lng": self.lng
        }

    def __hash__(self):
        return hash(self.name)

    def __eq__(self, other):
        return isinstance(other, City) and self.name == other.name

    def __repr__(self):
        return f"{self.name} ({self.type})"