from pandas.core.common import flatten
from sklearn.feature_extraction.text import CountVectorizer
import numpy as np
from sklearn.preprocessing import normalize as normalizer


def getJobFeatures(jobs, input_id=-1, knn=False):
    headers = []
    vectorString = ""
    input_vector_string = ""
    for job in jobs:
        #vectorString += job.minExperience.strip() + "," + job.jobType.strip() + "," + job.devType.strip() + "," + str(
        #    job.remote).strip() + "," + job.techs.strip()
        vectorString += job.minExperience.strip() +  "," + job.devType.strip() + "," + job.techs.strip()
        headers.append(vectorString)
        if knn and job.id == input_id:
            input_vector_string = vectorString
        vectorString = ""
    if knn:
        headers.remove(input_vector_string)
        headers.append(input_vector_string)
    return headers


def getUserBackgroundFeatures(background):
    vectorString = ""
    #vectorString += background[1] + "," + background[2] + "," + background[0] + "," + str(
    #    background[3]) + "," + background[4]
    vectorString += background[1] + "," + background[0] + "," + background[4]
    return vectorString


def vectorizeUserPreferences(jobs, preferences):
    vector = []
    found = False
    for job in jobs:
        for preference in preferences:
            if job.id == preference.job_id:
                found = True
                if preference.interested == True:
                    vector.append(1)
                elif preference.interested == False:
                    vector.append(-1)
                else:
                    vector.append(0)
                break
        if not found:
            vector.append(0)
        found = False
    return vector


def vectorizeJobsKNN(jobs, input_id):
    headers = getJobFeatures(jobs, input_id, True)

    vectorizer = CountVectorizer()
    vectorized_jobs = vectorizer.fit_transform(headers).toarray()
    normalized_jobs = normalizer(vectorized_jobs, 'l2')
    print("Number of vectorized entities: " + str(len(normalized_jobs)))

    vectorized_input = normalized_jobs[-1]
    normalized_jobs = np.delete(normalized_jobs, len(normalized_jobs) - 1, 0)
    return normalized_jobs, vectorized_input


def vectorizeDataCBF(jobs, preferences, background):
    headers = getJobFeatures(jobs)
    background_features = getUserBackgroundFeatures(background)
    headers.append(background_features)
    vectorized_preferences = vectorizeUserPreferences(jobs, preferences)
    vectorized_preferences = np.array(vectorized_preferences)

    vectorizer = CountVectorizer()
    normalized_preferences = list(flatten(normalizer(vectorized_preferences.reshape(1,-1), 'l2')))
    print("Normalized preference vector" + str(normalized_preferences))
    vectorized_jobs = vectorizer.fit_transform(headers).toarray()

    normalized_jobs = normalizer(vectorized_jobs, 'l2')
    print("Number of vectorized entities: " + str(len(normalized_jobs)))

    vectorized_background = normalized_jobs[-1]
    vectorized_jobs = np.delete(normalized_jobs, len(normalized_jobs) - 1, 0)

    return vectorized_jobs, normalized_preferences, vectorized_background
