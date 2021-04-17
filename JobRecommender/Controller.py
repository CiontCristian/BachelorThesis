from algorithms.KNN import KNN
from algorithms.vectorization import *
from db.DB import DB
from algorithms.CBF import contentBasedFiltering


class Controller:
    def __init__(self):
        self.db = DB()
        self.knn = KNN(k=7)

    def recommendKNN(self, input_id):
        jobs = self.db.getJobs()

        data = vectorizeJobsKNN(jobs, input_id)
        vectorizedJobs = data[0]
        query = data[1]
        print(query)

        self.knn.fit(vectorizedJobs, query)
        for job in jobs:
            if job.id == input_id:
                jobs.remove(job)
        res = self.knn.recommend(jobs)

        for job in res:
            print(job)
        return res

    def recommendCBF(self, input_id):
        jobs = self.db.getJobs()
        preferences = self.db.getUserPreferences(input_id)
        background = self.db.getUserBackground(input_id)

        data = vectorizeDataCBF(jobs, preferences, background)
        vectorized_jobs = data[0]
        vectorized_preferences = data[1]
        vectorized_background = data[2]

        similarity_values = contentBasedFiltering(vectorized_jobs, vectorized_preferences, vectorized_background)

        ids_similiraty = []
        for index, _ in enumerate(jobs):
            ids_similiraty.append((jobs[index].id, similarity_values[index]))

        ids_similiraty_desc = sorted(ids_similiraty, key=lambda x: x[1], reverse=True)
        print(ids_similiraty_desc)
        ids = [elem[0] for elem in ids_similiraty_desc]
        print(ids)
        return ids


