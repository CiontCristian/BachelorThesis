import math

import numpy as np
from collections import Counter


def euclidean_distance(x1, x2):
    return np.sqrt(np.sum((x1 - x2) ** 2))

#def euclidean_distance(point1, point2):
#    sum_squared_distance = 0
#    for i in range(len(point1)):
#        sum_squared_distance += math.pow(point1[i] - point2[i], 2)
#    return math.sqrt(sum_squared_distance)


class KNN:

    def __init__(self, k=3):
        self.k = k

    def fit(self, data, query):
        self.data = data
        self.query = query

    def predict(self, distance_fn):
        neighbor_distances_and_indices = []

        for index, example in enumerate(self.data):
            distance = distance_fn(example, self.query)
            print(str(index) + " " + str(distance) + " " + str(np.sum(example)) + " " + str(np.sum(self.query)))
            neighbor_distances_and_indices.append((distance, index))


        sorted_neighbor_distances_and_indices = sorted(neighbor_distances_and_indices)[:self.k]
        #k_nearest_distances_and_indices = sorted_neighbor_distances_and_indices[:self.k]

        print(sorted_neighbor_distances_and_indices)

        return sorted_neighbor_distances_and_indices

    def recommend(self, rawData):
        for index, example in enumerate(rawData):
            print(index, example.id)

        recommendation_indices = self.predict(
            distance_fn=euclidean_distance
        )

        recommended = []
        for _, index in recommendation_indices:
            recommended.append(rawData[index].id)

        return recommended
