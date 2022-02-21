FROM python:3.8

WORKDIR /app

COPY . .

RUN pip3 install -r requirements.txt

RUN ["python", "-c", "import nltk; nltk.download('stopwords')"]

EXPOSE 5000

CMD ["python3", "ML_CA_server.py"]