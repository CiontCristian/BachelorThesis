
import numpy as np


def euclidean_distance(x1, x2):
    return np.sqrt(np.sum((x1 - x2) ** 2))


class KNN:

    def __init__(self, k=3):
        self.k = k

    def fit(self, data, query):
        self.data = data
        print("Length:"+str(len(self.data)))
        self.query = query

    def predict(self, distance_fn):
        neighbor_distances_and_indices = []

        for index, example in enumerate(self.data):
            distance = distance_fn(example, self.query)
            neighbor_distances_and_indices.append((distance, index))

        sorted_neighbor_distances_and_indices = sorted(neighbor_distances_and_indices)[:self.k]
        print(sorted_neighbor_distances_and_indices)
        return sorted_neighbor_distances_and_indices

    def recommend(self, rawData):

        recommendation_indices = self.predict(
            distance_fn=euclidean_distance
        )

        recommended = []
        for _, index in recommendation_indices:
            recommended.append(rawData[index].id)

        return recommended
