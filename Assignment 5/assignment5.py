import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
import matplotlib.pyplot as plot
import time

df = pd.read_csv('16P.csv',encoding="macroman")
df_data = df.head(1000)
index_ = df_data.Personality
df_data.index = index_
df_new = df_data.drop(columns=['Response Id'])

df_new.loc[df_new['Personality'] == 'ESTJ', 'Personality'] = 0
df_new.loc[df_new['Personality'] == 'ENTJ', 'Personality'] = 1
df_new.loc[df_new['Personality'] == 'ESFJ', 'Personality'] = 2
df_new.loc[df_new['Personality'] == 'ENFJ', 'Personality'] = 3
df_new.loc[df_new['Personality'] == 'ISTJ', 'Personality'] = 4
df_new.loc[df_new['Personality'] == 'ISFJ', 'Personality'] = 5
df_new.loc[df_new['Personality'] == 'INTJ', 'Personality'] = 6
df_new.loc[df_new['Personality'] == 'INFJ', 'Personality'] = 7
df_new.loc[df_new['Personality'] == 'ESTP', 'Personality'] = 8
df_new.loc[df_new['Personality'] == 'ESFP', 'Personality'] = 9
df_new.loc[df_new['Personality'] == 'ENTP', 'Personality'] = 10
df_new.loc[df_new['Personality'] == 'ENFP', 'Personality'] = 11
df_new.loc[df_new['Personality'] == 'ISTP', 'Personality'] = 12
df_new.loc[df_new['Personality'] == 'ISFP', 'Personality'] = 13
df_new.loc[df_new['Personality'] == 'INTP', 'Personality'] = 14
df_new.loc[df_new['Personality'] == 'INFP', 'Personality'] = 15

data = np.array(df_new)
new = np.array(data[:,-1])
new = np.reshape(new,(len(data),1))
normalizedData = (data[:,:-1]-np.min(data[:,:-1]))/(np.max(data[:,:-1])-np.min(data[:,:-1]))
normalizedData = np.append(normalizedData,new,axis=1)

fold1 = data[:int(len(data)/5)]
fold2 = data[int(len(data)/5):int(len(data)/5)*2]
fold3 = data[int(len(data)/5)*2:int(len(data)/5)*3]
fold4 = data[int(len(data)/5)*3:int(len(data)/5)*4]
fold5 = data[int(len(data)/5)*4:]
train1 = np.vstack((fold2,fold3,fold4,fold5))
train2 = np.vstack((fold1,fold3,fold4,fold5))
train3 = np.vstack((fold1,fold2,fold4,fold5))
train4 = np.vstack((fold1,fold2,fold3,fold5))
train5 = np.vstack((fold1,fold2,fold3,fold4))
nfold1 = normalizedData[:int(len(data)/5)]
nfold2 = normalizedData[int(len(data)/5):int(len(data)/5)*2]
nfold3 = normalizedData[int(len(data)/5)*2:int(len(data)/5)*3]
nfold4 = normalizedData[int(len(data)/5)*3:int(len(data)/5)*4]
nfold5 = normalizedData[int(len(data)/5)*4:]
ntrain1 = np.vstack((nfold2,nfold3,nfold4,nfold5))
ntrain2 = np.vstack((nfold1,nfold3,nfold4,nfold5))
ntrain3 = np.vstack((nfold1,nfold2,nfold4,nfold5))
ntrain4 = np.vstack((nfold1,nfold2,nfold3,nfold5))
ntrain5 = np.vstack((nfold1,nfold2,nfold3,nfold4))
folders = [(fold1,train1),(fold2,train2),(fold3,train3),(fold4,train4),(fold5,train5)]
nfolders = [(nfold1,ntrain1),(nfold2,ntrain2),(nfold3,ntrain3),(nfold4,ntrain4),(nfold5,ntrain5)]


def most_frequent(List):
    return max(set(List), key=List.count)

A,B,C=0,0,0
def euclidean_distance(arr1, arr2):
    return np.linalg.norm(arr1 - arr2)

def euclidian_distances(fold, train):
    x2 = np.sum(fold**2, axis=1)
    y2 = np.sum(train**2, axis=1)
    xy = np.matmul(fold, train.T)
    x2 = x2.reshape(-1,1)
    distance = (x2 -2*xy + y2)**0.5
    print(distance)
    return distance

def sort(fold,train,k):
    dist = euclidian_distances(fold,train)
    a = np.argsort(dist)
    b = train[:,60][a]
    b = np.array(b,dtype='int64')


    u, indices = np.unique(b[:,:k], return_inverse=True)
    c = u[np.argmax(np.apply_along_axis(np.bincount, 1, indices.reshape(b[:,:k].shape),
                                    None, np.max(indices) + 1), axis=1)]
    arr = np.stack((fold[:,60],c),axis = 1)
    return arr


daccur = []
dprec = []
drecall = []
naccur = []
nprec = []
nrecall = []
time3 = time.time()
def kNN_algorithm(folder,accur,prec,recall):
    for k in range(1,10,2):
        time1 = time.time()
        Overall_recall,Overall_precision,Overall_accuracy = 0,0,0
        for m,n in folder:
            j = sort(m,n,k).tolist()
            overall_recall,overall_precision,overall_accuracy = 0,0,0
            classes = pd.DataFrame(j, columns=['Actual_Class','Pred_Class'])
            conf_modal1 = pd.crosstab(classes.Pred_Class,classes.Actual_Class)
            for i in range(conf_modal1.shape[0]):
                TP = conf_modal1.iloc[i,i]
                FP = conf_modal1.iloc[i,:].sum() - TP
                FN = conf_modal1.iloc[:,i].sum()- TP
                TN = conf_modal1.sum().sum()-TP-FP-FN
                Accuracy1 = (TP+TN)/conf_modal1.sum().sum()
                Precision1 = TP/(TP+FP)
                Recall1 = TP/(TP+FN)
                overall_recall += Recall1
                overall_precision += Precision1
                overall_accuracy += Accuracy1
            Overall_accuracy += overall_accuracy
            Overall_precision += overall_precision
            Overall_recall += overall_recall
            print(k)
            print(Overall_accuracy)
            print(Overall_precision)
            print(Overall_recall)
        time2= time.time()
        print(time2-time1)
        accur.append(Overall_accuracy/80)
        prec.append(Overall_precision/80)
        recall.append(Overall_recall/80)
kNN_algorithm(folders,daccur,dprec,drecall)
kNN_algorithm(nfolders,naccur,nprec,nrecall)
fig, axs = plot.subplots(3, 2)
x = range(1,10,2)
y = [daccur,dprec,drecall,naccur,nprec,nrecall]
axs[0, 0].plot(x, y[0])
axs[0, 0].set_title('Unnormalized Data\nAccuracy')
axs[0, 1].plot(x, y[3], 'tab:orange')
axs[0, 1].set_title('Normalized Data\nAccuracy')
axs[1, 0].plot(x, y[1], 'tab:green')
axs[1, 0].set_title('Precision')
axs[1, 1].plot(x, y[4], 'tab:red')
axs[1, 1].set_title('Precision')
axs[2, 0].plot(x, y[2], 'tab:green')
axs[2, 0].set_title('Recall')
axs[2, 1].plot(x, y[5], 'tab:red')
axs[2, 1].set_title('Recall')
for ax in axs.flat:
    ax.set(xlabel='k Values', ylabel='Ratio')

for ax in axs.flat:
    ax.label_outer()
plt.show()
time4 = time.time()
print(time4-time3)