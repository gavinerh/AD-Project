from array import array
from ast import Str
import json
from operator import contains
import pandas as pd
from flask import Flask, request, jsonify
import pickle
import string
import nltk
from nltk.corpus import stopwords
from nltk.stem import WordNetLemmatizer
from sklearn.feature_extraction.text import TfidfVectorizer # TF-IDF
from sklearn.metrics.pairwise import cosine_similarity # Cosine Similarity
import datetime
import requests;
import re

app = Flask(__name__)


lemmatizer = WordNetLemmatizer()

stop_words = stopwords.words('english')

def preprocess(docs):
    docs_clean = []
    # retitle = reTitle(docs)
    # doc_clean = clean(retitle)
    punc = str.maketrans('', '', string.punctuation)
    for doc in docs:
        doc_no_punc = doc.translate(punc)
        words = doc_no_punc.lower().split()    
        words = [lemmatizer.lemmatize(word, 'v')
                        for word in words if word not in stop_words]    
        docs_clean.append(' '.join(words))
    return docs_clean

def reTitle(titles):
    reTitles = []
    for title in titles:
        reTitles+=re.findall(re_title,title)
    print('reTitle\n',len(reTitles))
    return reTitles

def clean(titles):
    cleantitle = []
    for title in titles:
        title = str(title)
        str1 = ''
        str1 = str1.join(re.findall(no_nums,title))
        cleantitle.append(str1)
    print('clean\n',len(cleantitle))
    return cleantitle

tfidf = TfidfVectorizer()

re_content = re.compile('([\s\S]*)\[')
re_title = re.compile('(.*)\ -')
no_nums = re.compile('[a-zA-Z ]+')


@app.route('/getnews', methods=['POST'])
def jsontest():
    json_data = request.get_json()
    data = json_data
    
    return jsonify(data)

@app.route('/like', methods=['POST'])
def like():
    ## Cleaning and preprocessing to transform into feature vectors ##

     #Titles#
    json_data = request.get_json()
    title = json_data['titles']
    print(len(title))
    title = clean(title)
    docs_clean = preprocess(title)
    feature_vectors = tfidf.fit_transform(docs_clean).toarray()
    features = tfidf.get_feature_names_out()
    indexes = ['doc'+str(i) for i in range(len(title))]
    tfidf_df = pd.DataFrame(data=feature_vectors, index=indexes, columns=features)
    print('Tfidf_df size',len(tfidf_df.index))
    result={}
    ###Dislikes###
    dislikes = json_data['dislikedNews']
    if len(dislikes)>0:
        dislikes = clean(dislikes)
        dislike_clean = preprocess(dislikes)
        indx = []
        for st in dislike_clean:
            dq = [st]
            q_fv = tfidf.transform(dq).toarray()
            tfidf_q_fvdf = pd.DataFrame(data=q_fv,columns=features)
            c_similar = cosine_similarity(tfidf_q_fvdf,tfidf_df)
            q_sim = c_similar[0]
            sim_series = pd.Series(q_sim, index=tfidf_df.index)
            sorted_sim = sim_series.sort_values(ascending=False)
            sorted_sim = sorted_sim[sorted_sim!=0]
            ###Take only the highest score###
            doc_index = sorted_sim.index[0]
            
            if doc_index not in indx:
                indx.append(doc_index)
           
        for i in indx:
            print(i)
            tfidf_df = tfidf_df.drop(i)
        for index in tfidf_df.index:
            indexes = [] 
            doc_idx = int(index[3:])
            if doc_idx not in indexes:
                indexes.append(doc_idx) 
        result = {'result':indexes}
    print('Tfidf_df size after remove dislike',len(tfidf_df.index))
    ###Likes###
    likes = json_data['likedNews']
    if len(likes) > 0:
        likes = clean(likes)
        query_clean = preprocess(likes)
        idx = []
        for s in query_clean:
            query = [s]
            query_feature_vector = tfidf.transform(query).toarray()
            tfidf_q_df = pd.DataFrame(data=query_feature_vector,columns=features)
            similarity = cosine_similarity(tfidf_q_df, tfidf_df)
            query_sim = similarity[0]
            series = pd.Series(query_sim, index=tfidf_df.index)
            sorted_series = series.sort_values(ascending=False)
            sorted_series = sorted_series[sorted_series!=0]
            for index in sorted_series.index:
                doc_idx = int(index[3:])
                if doc_idx not in idx:
                    idx.append(doc_idx)
        result = {'result':idx}    
    print('result: ',len(result['result']))   
    return jsonify(result)



@app.route('/dislike', methods=['POST'])
def dislike():
    json_data = request.get_json()
    title = json_data['dislike']
    print(title)
    title = re.findall(re_title,title)
    print(title)
    process = preprocess(title)
    process = {'result':process}
    print(process)
    return jsonify(process)

# run the server
if __name__ == '__main__':
    app.run(port=5000, debug=True)
