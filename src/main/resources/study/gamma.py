
# UCI CS275P: Logistic Regression Classification

# Standard Imports
import numpy as np
import matplotlib.pyplot as plt

# Imports to use for optimization
from functools import partial
import scipy.optimize

# Local imports
from classifier_utils import plotter_classifier, generate_features, \
							example_of_quadratic_use, example_of_plot_function_with_toy_dataset

def loss_func_logistic(w, x, t, K, alpha):
	''' Compute the L2-regularized logistic loss function

		Arguments:
			w: estimated feature weight vector
			x: model matrix (NxM)
			t: response vector (Nx1)
			K: number of response classes
			alpha: regularization constant

		Returns:
			The loss function value
	'''

	loss = ...
	return loss

def gradient_loss_func_logistic(w, x, t, K, alpha):
	''' Compute the gradient of the L2-regularized logistic loss (with respect to w)

		Arguments:
			w: estimated feature weight vector
			x: model matrix (NxM)
			t: response vector (Nx1)
			K: number of response classes
			alpha: regularization constant

		Returns:
			The gradient of loss function with respect to w
	'''

	gradient = ...
	return gradient

def gamma_logistic_regression_model(phi_train, phi_test, train_labels, test_labels, options, alpha=1e-6):
    ''' Train a logistic regression model on gamma dataset

		Arguments:
			phi_train: training dataset (NxM)
			phi_test: testing dataset (N'xM)
			train_labels: vector of training labels (Nx1)
			test_labels: vector of testing labels (N'x1)
            options: Dictionary object for scipy.optimize.minimize (dict())
            alpha: alpha value used in logistic loss and gradient functions (float)

            (N: number of elements in training data, N': number of elements in testing data)
		Returns:
			train_accuracy: (float)
		    train_loss: (float)
			test_accuracy: (float)
			test_loss: (float)
	'''

    train_loss = ...
    test_loss = ...

    train_accuracy = ...
    test_accuracy = ...

    return train_accuracy, train_loss, test_accuracy, test_loss

def toy_linear_regression_model(phi_train, phi_test, train_labels, test_labels):
    ''' Train a linear regression model on toy dataset

		Arguments:
			phi_train: training dataset with added bias term (NxM)
			phi_test: testing dataset with added bias term (N'xM)
			train_labels: matrix of one-hot encoded training labels (NxK)
			test_labels: matrix of one-hot encoded testing labels (N'xK)

            (N: number of elements in training data, N': number of elements in testing data)
		Returns:
			train_err: overall training error (float)
			test_err: overall testing error (float)
			w_hat: weights corresponding to the least squares prediction (MxK)
	'''
    print("\tTraining linear regression model...")

    w_hat = ...

    train_err = ...
    test_err = ...


    return train_err, test_err, w_hat

def toy_logistic_regression_model(phi_train, phi_test, train_labels, test_labels, options, alpha=1e-6):
    ''' Train a linear regression model on toy dataset

		Arguments:
			phi_train: training dataset with added bias term (NxM)
			phi_test: testing dataset with added bias term (N'xM)
			train_labels: matrix of one-hot encoded training labels (NxK)
			test_labels: matrix of one-hot encoded testing labels (N'xK)
            options: Dictionary object for scipy.optimize.minimize (dict())
            alpha: alpha value used in logistic loss and gradient functions (float)

            (N: number of elements in training data, N': number of elements in testing data)
		Returns:
			train_err: overall training error (float)
			test_err: overall testing error (float)
			w_hat: weights corresponding to the logistic regression prediction (MxK)
	'''

    w_hat = ...

    train_err = ...
    test_err = ...

    return train_err, test_err, w_hat


if __name__ == "__main__":
    # example functions:
    example_of_quadratic_use()

    #break to observe output
    input("Press Enter to continue to toy dataset example...")

    example_of_plot_function_with_toy_dataset()

