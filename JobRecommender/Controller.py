from algorithms.KNN import KNN
from dataset.DataProcessing import getFeatureVectorHeaders
from db.DB import DB


class Controller:
    def __init__(self):
        self.db = DB()
        self.knn = KNN(k=7)

    def recommendKNN(self, input_id):
        jobs = self.db.getJobs()

        data = getFeatureVectorHeaders(jobs, input_id)
        vectorizedJobs = data[0]
        query = data[1][0]

        self.knn.fit(vectorizedJobs, query)
        res = self.knn.recommend(jobs)

        return res

    def recommendCBF(self):
        pass
