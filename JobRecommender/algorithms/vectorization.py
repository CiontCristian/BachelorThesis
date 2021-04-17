
from sklearn.feature_extraction.text import CountVectorizer
import numpy as np


def getJobFeatures(jobs, input_id=-1, knn=False):
    headers = []
    vectorString = ""
    input_vector_string = ""
    for job in jobs:
        vectorString += job.minExperience.strip() + "," + job.jobType.strip() + "," + job.devType.strip() + "," + str(
            job.remote).strip() + "," + job.techs.strip()
        headers.append(vectorString)
        if knn and job.id == input_id:
            input_vector_string = vectorString
        vectorString = ""
    if knn:
        headers.append(input_vector_string)
    return headers


def getUserBackgroundFeatures(background):
    vectorString = ""
    vectorString += background[1] + "," + background[2] + "," + background[0] + "," + str(
        background[3]) + "," + background[4]
    return vectorString


def vectorizeUserPreferences(jobs, preferences):
    vector = []
    found = False
    for job in jobs:
        for preference in preferences:
            if job.id == preference.job_id:
                found = True
                if preference.interested:
                    vector.append(1)
                else:
                    vector.append(-1)
                break
        if not found:
            vector.append(0)
        found = False

    return vector


def vectorizeJobsKNN(jobs, input_id):
    headers = getJobFeatures(jobs, input_id, True)

    vectorizer = CountVectorizer()
    vectorized_matrix = vectorizer.fit_transform(headers)
    print(vectorizer.get_feature_names())
    # print(vectorized_matrix.toarray())
    vectorized_matrix = vectorized_matrix.toarray()

    vectorized_input = vectorized_matrix[-1]
    print(vectorized_input)
    vectorized_matrix = np.delete(vectorized_matrix, len(vectorized_matrix) - 1, 0)

    return vectorized_matrix, vectorized_input


def vectorizeDataCBF(jobs, preferences, background):
    headers = getJobFeatures(jobs)
    background_features = getUserBackgroundFeatures(background)
    headers.append(background_features)
    vectorized_preferences = vectorizeUserPreferences(jobs, preferences)

    vectorizer = CountVectorizer()
    vectorized_jobs = vectorizer.fit_transform(headers).toarray()

    vectorized_background = vectorized_jobs[-1]
    vectorized_jobs = np.delete(vectorized_jobs, len(vectorized_jobs) - 1, 0)

    return vectorized_jobs, vectorized_preferences, vectorized_background
