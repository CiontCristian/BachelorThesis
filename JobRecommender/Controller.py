from algorithms.KNN import KNN
from dataset.DataProcessing import *
from db.DB import DB


class Controller:
    def __init__(self):
        self.db = DB()
        self.knn = KNN(k=7)

    def recommendKNN(self, input_id):
        jobs = self.db.getJobs()

        print(len(jobs))
        #data = getFeatureVectorHeaders(jobs, input_id)
        data = vectorizeJobs(jobs, input_id)
        vectorizedJobs = data[0]
        query = data[1]
        print(query)
        '''
        435
        437
        448
        478
        442
        450
        480
        '''
        #vectorizedJobs = np.array(vectorizedJobs)
        #query = np.array(query)

        self.knn.fit(vectorizedJobs, query)
        for job in jobs:
            if job.id == input_id:
                jobs.remove(job)
        res = self.knn.recommend(jobs)

        for job in res:
            print(job)
        return res

    def recommendCBF(self, input_id):
        preferences = self.db.getUserPreferences(input_id)
        for a in preferences:
            print(a)


