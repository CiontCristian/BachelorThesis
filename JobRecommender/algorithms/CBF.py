
from pandas.core.common import flatten
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np


def contentBasedFiltering(vectorized_jobs, vectorized_preferences, vectorized_background):

    transpose_vectorized_jobs = vectorized_jobs.transpose()
    #print("Background: " + str(vectorized_background))
    #print("Preferences: " + str(vectorized_preferences))
    preferences_weight = np.dot(transpose_vectorized_jobs, vectorized_preferences)
    #print("Preference weights: " + str(preferences_weight))

    preference_vector_updated = vectorized_background + preferences_weight
   #print("Background with preferences: " + str(preference_vector_updated))

    similarity_vector = cosine_similarity(vectorized_jobs, preference_vector_updated.reshape(1, -1))
    similarity_vector = list(flatten(similarity_vector))
    #print("Similarity vector " + str(similarity_vector))

    return similarity_vector
