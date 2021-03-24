import math

import numpy as np
from collections import Counter


#def euclidean_distance(x1, x2):
#    return np.sqrt(np.sum((x1 - x2) ** 2))

def euclidean_distance(point1, point2):
    sum_squared_distance = 0
    for i in range(len(point1)):
        sum_squared_distance += math.pow(point1[i] - point2[i], 2)
    return math.sqrt(sum_squared_distance)


class KNN:

    def __init__(self, k=3):
        self.k = k

    def fit(self, data, query):
        self.data = data
        self.query = query

    def predict(self, distance_fn):
        neighbor_distances_and_indices = []

        # 3. For each example in the data
        for index, example in enumerate(self.data):
            # 3.1 Calculate the distance between the query example and the current
            # example from the data.
            distance = distance_fn(example[:-1], self.query)

            # 3.2 Add the distance and the index of the example to an ordered collection
            neighbor_distances_and_indices.append((distance, index))

        # 4. Sort the ordered collection of distances and indices from
        # smallest to largest (in ascending order) by the distances
        sorted_neighbor_distances_and_indices = sorted(neighbor_distances_and_indices)

        # 5. Pick the first K entries from the sorted collection
        k_nearest_distances_and_indices = sorted_neighbor_distances_and_indices[:self.k]

        return k_nearest_distances_and_indices

    def recommend(self, rawData):
        recommendation_indices = self.predict(
            distance_fn=euclidean_distance
        )

        recommended = []
        for _, index in recommendation_indices:
            recommended.append(rawData[index])

        return recommended
