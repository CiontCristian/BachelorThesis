
from pandas.core.common import flatten
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np


def contentBasedFiltering(vectorized_jobs, vectorized_preferences, vectorized_background):

    transpose_vectorized_jobs = vectorized_jobs.transpose()
    print(len(vectorized_jobs[0]))

    dot_prod_1 = np.dot(transpose_vectorized_jobs, vectorized_preferences)

    vectorized_preferences = np.array(vectorized_preferences)
    print(vectorized_preferences)
    print(dot_prod_1)

    preference_vector_updated = vectorized_background + dot_prod_1

    print(preference_vector_updated)

    final = cosine_similarity(vectorized_jobs, preference_vector_updated.reshape(1, -1))
    final = list(flatten(final))

    return final
