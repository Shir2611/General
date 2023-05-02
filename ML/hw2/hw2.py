###### Your ID ######
# ID1: 208000281
# ID2: 208128793
#####################

import queue

import matplotlib.pyplot as plt
import numpy as np
import pandas as pd

### Chi square table values ###
# The first key is the degree of freedom
# The second key is the p-value cut-off
# The values are the chi-statistic that you need to use in the pruning

chi_table = {1: {0.5: 0.45,
             0.25: 1.32,
             0.1: 2.71,
             0.05: 3.84,
             0.0001: 100000},
             2: {0.5: 1.39,
             0.25: 2.77,
             0.1: 4.60,
             0.05: 5.99,
             0.0001: 100000},
             3: {0.5: 2.37,
             0.25: 4.11,
             0.1: 6.25,
             0.05: 7.82,
             0.0001: 100000},
             4: {0.5: 3.36,
             0.25: 5.38,
             0.1: 7.78,
             0.05: 9.49,
             0.0001: 100000},
             5: {0.5: 4.35,
             0.25: 6.63,
             0.1: 9.24,
             0.05: 11.07,
             0.0001: 100000},
             6: {0.5: 5.35,
             0.25: 7.84,
             0.1: 10.64,
             0.05: 12.59,
             0.0001: 100000},
             7: {0.5: 6.35,
             0.25: 9.04,
             0.1: 12.01,
             0.05: 14.07,
             0.0001: 100000},
             8: {0.5: 7.34,
             0.25: 10.22,
             0.1: 13.36,
             0.05: 15.51,
             0.0001: 100000},
             9: {0.5: 8.34,
             0.25: 11.39,
             0.1: 14.68,
             0.05: 16.92,
             0.0001: 100000},
             10: {0.5: 9.34,
                  0.25: 12.55,
                  0.1: 15.99,
                  0.05: 18.31,
                  0.0001: 100000},
             11: {0.5: 10.34,
                  0.25: 13.7,
                  0.1: 17.27,
                  0.05: 19.68,
                  0.0001: 100000}}


def calc_gini(data):
    """
    Calculate gini impurity measure of a dataset.

    Input:
    - data: any dataset where the last column holds the labels.

    Returns:
    - gini: The gini impurity value.
    """
    gini = 0.0
    rightmost_column = data[:, -1]
    column_size = len(data)
    unique_values, corr_count = np.unique(rightmost_column, return_counts=True)
    #gini calculation
    sum_of_prob = corr_count / column_size
    gini = 1 - np.sum(np.power(sum_of_prob,2))
    return gini


def calc_entropy(data):
    """
    Calculate the entropy of a dataset.

    Input:
    - data: any dataset where the last column holds the labels.

    Returns:
    - entropy: The entropy value.
    """
    entropy = 0.0
    rightmost_column = data[:, -1]
    column_size = len(data)
    unique_values, corr_count = np.unique(rightmost_column, return_counts=True)
    #entropy calculation
    curr_prob = corr_count / column_size
    entropy = np.sum(curr_prob * np.log2(curr_prob))
    entropy = -entropy
    return entropy


def goodness_of_split(data, feature, impurity_func, gain_ratio=False):
    """
    Calculate the goodness of split of a dataset given a feature and impurity function.
    Note: Python support passing a function as arguments to another function
    Input:
    - data: any dataset where the last column holds the labels.
    - feature: the feature index the split is being evaluated according to.
    - impurity_func: a function that calculates the impurity.
    - gain_ratio: goodness of split or gain ratio flag.

    Returns:
    - goodness: the goodness of split value
    - groups: a dictionary holding the data after splitting 
              according to the feature values.
    """
    goodness = 0
    groups = {}  # groups[feature_value] = data_subset
    feature_column = data[:, feature]
    unique_values, corr_count = np.unique(feature_column, return_counts=True)
    column_size = len(feature_column)
    data_impuriy = impurity_func(data)

    if not gain_ratio:  # calculating according to impurity_func
        for index, feature_value in enumerate(unique_values):
            data_subset = data[feature_column == feature_value]
            curr_prob = (corr_count[index] / column_size)
            goodness -= curr_prob*impurity_func(data_subset)
            groups[feature_value] = data_subset
        goodness += data_impuriy

    else:  # calculating according to gain_ratio_func
        info_gain = calc_entropy(data)
        split_info = 0.0
        for index, feature_value in enumerate(unique_values):
            data_subset = data[feature_column == feature_value]
            curr_prob = (corr_count[index] / column_size)
            info_gain -= curr_prob*calc_entropy(data_subset)
            groups[feature_value] = data_subset
            split_info -= curr_prob*np.log2(curr_prob)
        if split_info != 0:
            goodness = info_gain / split_info
        else:
            goodness = 0
    return goodness, groups


class DecisionNode:

    def __init__(self, data, feature=-1, depth=0, chi=1, max_depth=1000, gain_ratio=False):

        self.data = data  # the relevant data for the node
        self.feature = feature  # column index of criteria being tested
        self.pred = self.calc_node_pred()  # the prediction of the node
        self.depth = depth  # the current depth of the node
        self.children = []  # array that holds this nodes children
        self.children_values = []
        self.terminal = False  # determines if the node is a leaf
        self.chi = chi
        self.max_depth = max_depth  # the maximum allowed depth of the tree
        self.gain_ratio = gain_ratio

    def calc_node_pred(self):
        """
        Calculate the node prediction.

        Returns:
        - pred: the prediction of the node
        """
        pred = None
        rightmost_column = self.data[:, self.feature]
        unique_values, corr_count = np.unique(rightmost_column, return_counts=True)
        max_value, corr_max_count = 0, 0
        
        #find the prediction of the node.
        corr_max_count = np.max(corr_count)
        max_value = unique_values[np.where(corr_count == corr_max_count)[0][0]]
        pred = max_value
        return pred

    def add_child(self, node, val):
        """
        Adds a child node to self.children and updates self.children_values

        This function has no return value
        """
        self.children.append(node)
        self.children_values.append(val)

    def split(self, impurity_func):
        """
        Splits the current node according to the impurity_func. This function finds
        the best feature to split according to and create the corresponding children.
        This function should support pruning according to chi and max_depth.

        Input:
        - The impurity function that should be used as the splitting criteria

        This function has no return value
        """

        #checking if we reach to the max depth of the tree.
        if self.max_depth == self.depth:
            self.terminal = True
            return

        degree_of_freedom = len(np.unique(self.data[:, self.feature])) - 1

        # Check if the chi value equals 1 then no need to preform chi pruning.
        if self.chi == 1:
            min_value = 0.0 
        else:
            min_value = chi_table[degree_of_freedom][self.chi]

        #cakculating the chi value by the given formula
        current_chi_value = calc_chi_squared(self.feature, self.data)

        if current_chi_value < min_value:
            self.terminal = True
            return

        b_feature = best_feature(self.data, impurity_func, self.gain_ratio)
        best_col = self.data[:, b_feature]
        unique_values = np.unique(best_col)

        for value in unique_values:
            data_value_matrix = self.data[best_col == value]
            child = DecisionNode(data=data_value_matrix, depth=self.depth+1, chi=self.chi, max_depth=self.max_depth, gain_ratio=self.gain_ratio)
            self.add_child(child, value)


#we built this function
def calc_chi_squared(feature, data):
    """
        calc the chi square using the given formula

        Input:
        - feature: the index of the spesific feature that we are calculating the chi square.
        - data: the dataset which the last column contains the labels.
        
        Returns:
        - chi_squared: the chi square of the dataset according to the feature we have.
        """
        
    chi_squared = 0
    column_size = len(data[:, -1])
    unique_values, unique_count = np.unique(data[:, feature], return_counts=True)
    class_values, class_count = np.unique(data[:, -1], return_counts=True)

    p_y_zero = class_count[0]/column_size
    p_y_one = 1 - p_y_zero

    for index, value in enumerate(unique_values):
        d_f = unique_count[index]
        p_f = np.sum((value == data[:, feature]) & (class_values[0] == data[:,-1]))
        n_f = np.sum((value == data[:, feature]) & (class_values[1] == data[:,-1]))
        e_zero = d_f * p_y_zero
        e_one = d_f * p_y_one
        # Calculate the chi square according to the given formula.
        chi_squared += np.sum((p_f - e_zero)**2 / e_zero + (n_f - e_one)**2 / e_one)
    return chi_squared


#we built this function
def best_feature(data, impurity_func, gain_ratio=False):
    """
    Calculates the best feature that we have in the data.
    Returns the best feature's index
    
    Input:
    - data: the dataset which the last column contains the labels.
    - impurity_func: The function that used as the splitting criteria. 
    - gain_ratio: goodness of split or gain ratio flag.

    Returns:
    - best_feature_index: the index of the criteria that we should split by.
    """

    best_feature_index = None
    num_of_features = len(data[0]) - 1
    max_goodness_of_split = 0
    
    # Going over all the features and calc the goodness of split.
    for feature in range(num_of_features):
        curr_goodness, _ = goodness_of_split(data, feature, impurity_func, gain_ratio)
       
        # finding the index of the feature that has the maximal goodness of split.
        if curr_goodness > max_goodness_of_split:
            max_goodness_of_split = curr_goodness
            best_feature_index = feature

    return best_feature_index


def build_tree(data, impurity, gain_ratio=False, chi=1, max_depth=1000):
    """
    Build a tree using the given impurity measure and training dataset. 
    You are required to fully grow the tree until all leaves are pure unless
    you are using pruning

    Input:
    - data: the training dataset.
    - impurity: the chosen impurity measure. Notice that you can send a function
                as an argument in python.
    - gain_ratio: goodness of split or gain ratio flag

    Output: the root node of the tree.
    """
    root = DecisionNode(data=data, chi=chi, max_depth=max_depth, gain_ratio=gain_ratio)
    
    #insert the nodes according their order (queue - best data structure to do so)
    Q = queue.Queue()
    Q.put(root)
    while not Q.empty():
        new_node = Q.get()
        #check if the data is classified (using our help method)
        if is_classified(new_node):
            new_node.terminal = True
            continue
        #insert the node the best feature to split by.
        new_node.feature = best_feature(new_node.data, impurity, gain_ratio)
        #if we don't have a feature to split by.
        if new_node.feature == None:
            new_node.terminal = True
            continue
        #by the impurity fun.
        new_node.split(impurity)
        
        #insert the children of the current node to the tree (via the queue).
        for child in new_node.children:
            Q.put(child)
    return root


#we built this function
def is_classified(node):
    """
    Input:
    - node: the current node.
    
    Output: if the training examples in the node are perfectly classified.
    """
    unique_values = np.unique(node.data)
    if len(unique_values) == 1:
        return True
    return False


def predict(root, instance):
    """
    Predict a given instance using the decision tree

    Input:
    - root: the root of the decision tree.
    - instance: an row vector from the dataset. Note that the last element 
                of this vector is the label of the instance.

    Output: the prediction of the instance.
    """
    #runs as long as we in the tree and we don't reach to a leaf.
    while not root.terminal is True:
        try:
            next_child_index = root.children_values.index(instance[root.feature])
            root = root.children[next_child_index]
        except:
            break
        
    #using the prediction of the node.
    pred = root.pred
    return pred


def calc_accuracy(node, dataset):
    """
    Predict a given dataset using the decision tree and calculate the accuracy

    Input:
    - node: a node in the decision tree.
    - dataset: the dataset on which the accuracy is evaluated

    Output: the accuracy of the decision tree on the given dataset (%).
    """
    accuracy = 0
    column_size = len(dataset)

    #iterating over all the rows in the dataset.
    for row in dataset:
        #using the prediction of the row according to predict fun. and real value.
        if row[-1] == predict(node, row):
            accuracy += 1
    
    # calc accuracy of tree.
    accuracy = accuracy / column_size
    return accuracy


def depth_pruning(X_train, X_test):
    """
    Calculate the training and testing accuracies for different depths
    using the best impurity function and the gain_ratio flag you got
    previously.

    Input:
    - X_train: the training data where the last column holds the labels
    - X_test: the testing data where the last column holds the labels

    Output: the training and testing accuracies per max depth
    """
    training = []
    testing = []
    
    #going on all the depths.
    for max_depth in [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]:
        #creating a tree using the max_depth and compute the accuracy of the tree according to the dataset.
        current_tree = build_tree(X_train, impurity=calc_entropy, gain_ratio=True, max_depth=max_depth)
        training.append(calc_accuracy(current_tree, X_train))
        testing.append(calc_accuracy(current_tree, X_test))
    return training, testing


def chi_pruning(X_train, X_test):
    """
    Calculate the training and testing accuracies for different chi values
    using the best impurity function and the gain_ratio flag you got
    previously. 

    Input:
    - X_train: the training data where the last column holds the labels
    - X_test: the testing data where the last column holds the labels

    Output:
    - chi_training_acc: the training accuracy per chi value
    - chi_testing_acc: the testing accuracy per chi value
    - depths: the tree depth for each chi value
    """
    chi_training_acc = []
    chi_testing_acc = []
    depth = []
    
    #going over all the p_values.
    for chi in [1, 0.5, 0.25, 0.1, 0.05, 0.0001]:
        #creating a new tree that its chi value calculated according to the p_values.
        current_tree = build_tree(X_train, impurity=calc_entropy, gain_ratio=True, chi=chi)
        #calc the accuracy and the depth of the tree.
        chi_training_acc.append(calc_accuracy(current_tree, X_train))
        chi_testing_acc.append(calc_accuracy(current_tree, X_test))
        depth.append(calc_tree_depth(current_tree))
        
    return chi_training_acc, chi_testing_acc, depth


#we built this function
def calc_tree_depth(node):
    """
    Calculates the depth of the decision tree
    with regarding the it's given root

    Input:
    - node: a node from a tree.

    Output: the depth of the tree.
    """
    
    #if the node is a leaf.
    if node.terminal:
        return node.depth
    
    #will contain the depth of the node's children.
    tree_depths = []
    
    #going over all the children of the node.
    for child in node.children:
        child_depth = calc_tree_depth(child)
        tree_depths.append(child_depth)
    
    return max(tree_depths)


def count_nodes(node):
    """
    Count the number of node in a given tree

    Input:
    - node: a node in the decision tree.

    Output: the number of nodes in the tree.
    """
    n_nodes = 0
    
    #check if node exist.
    if node == None:
        return 1
    #count this node and count his children nodes.
    for child in node.children:
        n_nodes += count_nodes(child) 
    n_nodes += 1

    return n_nodes
