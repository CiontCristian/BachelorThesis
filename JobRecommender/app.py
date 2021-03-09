
from flask import Flask, request, jsonify, abort
from flask_cors import CORS, cross_origin


app = Flask(__name__)
cors = CORS(app)


@app.route('/')
def hello_world():
    return 'Hello World!'


@app.route('/recommender/saveRecommenderInfo', methods=["POST"], strict_slashes=False)
@cross_origin()
def saveRecommenderInfo():
    info = request.get_json(force=True)
    print('In method: saveRecommenderInfo, info= '+str(info))
    return jsonify(info), 200


@app.route('/recommender/getRecommendedJobsIds', methods=["GET"], strict_slashes=False)
def getRecommendedJobsIds():
    jobs_ids = [1,2,3]
    response = jsonify(jobs_ids)
    response.headers.add("Access-Control-Allow-Origin", "*")

    return response, 200


if __name__ == '__main__':
    app.run()
