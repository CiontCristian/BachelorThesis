from knn.KNN import KNN
from dataset.DataProcessing import *


def getKNNRecommendations(input_id):
    knn = KNN(k=7)

    jobs = readFromFile("../jobs.txt")
    data = getFeatureVectorHeaders(jobs, input_id)
    vectorizedJobs = data[0]
    query = data[1][0]
    print(query)
    knn.fit(vectorizedJobs, query)
    res = knn.recommend(jobs)
    for job in res:
        print(job)

    return res


getKNNRecommendations(24235)
