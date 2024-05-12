"""
At the command line, only need to run once to install the package via pip:

$ pip install google-generativeai
$ pip install flask flask-sqlalchemy
"""

import google.generativeai as genai
from flask import Flask, request, jsonify

properties_file_path = '../resources/application.properties'

gemini_API_key = 'YOUR-API-KEY-OR-IN-application.properties-FILE'  # Not recommended for the key to be hard coded

properties_file = open(properties_file_path)
for line in properties_file:
    if line.startswith("mashoora.app.geminiApiKey"):
        gemini_API_key = line.replace("mashoora.app.geminiApiKey=", '').strip()

genai.configure(api_key=gemini_API_key)

# Set up the model
generation_config = {
    "temperature": 1,
    "top_p": 0.95,
    "top_k": 0,
    "max_output_tokens": 8192,
}

safety_settings = [
    {
        "category": "HARM_CATEGORY_HARASSMENT",
        "threshold": "BLOCK_NONE"
    },
    {
        "category": "HARM_CATEGORY_HATE_SPEECH",
        "threshold": "BLOCK_MEDIUM_AND_ABOVE"
    },
    {
        "category": "HARM_CATEGORY_SEXUALLY_EXPLICIT",
        "threshold": "BLOCK_NONE"
    },
    {
        "category": "HARM_CATEGORY_DANGEROUS_CONTENT",
        "threshold": "BLOCK_NONE"
    },
]

system_instruction = open("Adel-Instructions.txt").read()

model = genai.GenerativeModel(model_name="gemini-1.5-pro-latest",
                              generation_config=generation_config,
                              system_instruction=system_instruction,
                              safety_settings=safety_settings)

app = Flask(__name__)


@app.route('/api/adel', methods=['POST'])
def call_adel():
    data = request.json
    query = data.get('query')
    previous_conversations = data.get('previousConversations')

    if query is None:
        return jsonify({'error': 'Query is a required field'}), 400

    convo = model.start_chat(history=previous_conversations)
    try:
        convo.send_message(query)
        return jsonify(convo.last.text)
    except:
        return jsonify({'error': 'Error sending message or retrieving response'}), 500


if __name__ == "__main__":
    app.run(debug=True, port=8000)
