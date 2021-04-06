from flask import Flask, request, jsonify
from flask_cors import CORS, cross_origin
from dataset.DataProcessing import *
from knn.KNN_test import getKNNRecommendations

app = Flask(__name__)
cors = CORS(app)


@app.route('/')
def hello_world():
    return 'Hello World!'


@app.route('/recommender/saveRecommenderInfo', methods=["POST"], strict_slashes=False)
@cross_origin()
def saveRecommenderInfo():
    info = request.get_json(force=True)
    print('In method: saveRecommenderInfo, info= ' + str(info))
    return jsonify(info), 200


@app.route('/recommender/getRecommendedJobsIds', methods=["POST"], strict_slashes=False)
def getRecommendedJobsIds():
    input_id = request.get_json(force=True)
    jobs_ids = getKNNRecommendations(int(input_id))
    response = jsonify(jobs_ids)
    response.headers.add("Access-Control-Allow-Origin", "*")

    return response, 200


@app.route('/recommender/getJobs', methods=["GET"], strict_slashes=False)
def getJobs():
    jobs = cleanDataset()
    response = jsonify([job.serialize() for job in jobs])
    response.headers.add("Access-Control-Allow-Origin", "*")

    return response, 200


if __name__ == '__main__':
    app.run()
