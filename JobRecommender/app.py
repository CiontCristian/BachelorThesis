from flask import Flask, request, jsonify
from flask_cors import CORS

from Controller import Controller
from dataset.DataProcessing import *

app = Flask(__name__)
cors = CORS(app)

ctr = Controller()


@app.route('/recommender/getRecommendedJobsIdsKNN', methods=["POST"], strict_slashes=False)
def getRecommendedJobsIdsKNN():
    input_id = request.get_json(force=True)
    global ctr
    jobs_ids = ctr.recommendKNN(int(input_id))
    response = jsonify(jobs_ids)
    response.headers.add("Access-Control-Allow-Origin", "*")

    return response, 200


@app.route('/recommender/getRecommendedJobsIdsCBF', methods=["POST"], strict_slashes=False)
def getRecommendedJobsIdsCBF():
    input_id = request.get_json(force=True)
    global ctr
    jobs_ids = ctr.recommendCBF(int(input_id))
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
