from algorithms.KNN import KNN
from algorithms.vectorization import *
from db.DB import DB
from algorithms.CBF import contentBasedFiltering


class Controller:
    def __init__(self):
        self.db = DB()
        self.knn = KNN(k=7)

    def recommendKNN(self, job_id, user_id):
        jobs = self.db.getJobs()
        data = vectorizeJobsKNN(jobs, job_id)
        vectorizedJobs = data[0]
        query = data[1]

        self.knn.fit(vectorizedJobs, query)

        jobs = [job for job in jobs if job.id != job_id]
        if user_id != -1:
            bannedIds = self.db.getRatedJobsKNN(user_id)
        else:
            bannedIds = []

        ids_distance = self.knn.recommend(jobs, len(bannedIds))
        print("Lowest distances: " + str(ids_distance))
        print("Banned ids for KNN:" + str(bannedIds))

        ids_distance = [elem for elem in ids_distance if elem[0] not in bannedIds]
        print("Lowest unbanned distances: " + str(ids_distance))

        ids_distance = [elem[0] for elem in ids_distance]
        return ids_distance

    def recommendCBF(self, input_id):

        jobs = self.db.getJobs()
        preferences = self.db.getUserPreferences(input_id)
        background = self.db.getUserBackground(input_id)

        data = vectorizeDataCBF(jobs, preferences, background)
        vectorized_jobs = data[0]
        vectorized_preferences = data[1]
        vectorized_background = data[2]

        similarity_values = contentBasedFiltering(vectorized_jobs, vectorized_preferences, vectorized_background)

        ids_similarity = []
        for index, _ in enumerate(jobs):
            ids_similarity.append((jobs[index].id, similarity_values[index]))

        ids_similarity_desc = sorted(ids_similarity, key=lambda x: x[1], reverse=True)
        print("Highest similarities: " + str(ids_similarity_desc))

        ratedJobIds = self.db.getRatedJobsCBF(input_id)
        print("Banned ids for CNF:" + str(ratedJobIds))

        ids_similarity_desc = [elem for elem in ids_similarity_desc if elem[0] not in ratedJobIds]
        print("Highest unrated similarities: " + str(ids_similarity_desc))

        ids = [elem[0] for elem in ids_similarity_desc]
        return ids
