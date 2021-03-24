import numpy as np
from sklearn import datasets
from sklearn.model_selection import train_test_split
import matplotlib.pyplot as plt
from matplotlib.colors import ListedColormap
from KNN import KNN
from dataset.DataProcessing import *

knn = KNN(k=7)

jobs = cleanDataset()
vectorizedJobs = getFeatureVectorHeaders(jobs)

query = random.choice(vectorizedJobs)
vectorizedJobs.remove(query)

knn.fit(vectorizedJobs, query)
res = knn.recommend(jobs)
for job in res:
    print(job)
