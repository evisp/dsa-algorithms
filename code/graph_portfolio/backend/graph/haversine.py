import math


def haversine(city_a, city_b):
    """
    Calculate the great-circle distance in km between two cities
    using their latitude and longitude. Used as the A* heuristic.
    """
    R = 6371  # Earth's radius in km

    lat1 = math.radians(city_a.lat)
    lat2 = math.radians(city_b.lat)
    dlat = math.radians(city_b.lat - city_a.lat)
    dlng = math.radians(city_b.lng - city_a.lng)

    a = math.sin(dlat / 2) ** 2 + \
        math.cos(lat1) * math.cos(lat2) * \
        math.sin(dlng / 2) ** 2

    c = 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a))

    return R * c