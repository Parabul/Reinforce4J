# UCI CS275P: Linear regression with motorcycle response data


# Standard Imports
import numpy as np
import matplotlib.pyplot as plt

# Local Imports
from basis_utils import basis_poly, basis_radial, generate_basis_matrices, rescale_inputs


def compute_weights(phi_train, y_train, lambda_val):
    """
    Compute the ML or MAP estimates of weights for linear regression.

    Args:
        phi_train: Training basis matrix (size NxM, for N training data and M features)
        y_train: Training target labels (size Nx1)
        lambda_val: Regularization parameter (alpha/beta) for MAP weight estimation.
                    lambda_val = 0 for ML estimation

    Returns:
        weights: Computed regression weights (size Mx1)
    """

    # Compute the weights
    # Solve rather than inverting matrix for numerical stability

    pass


def compute_errors_for_models(orders_to_try, x_train_rescaled, y_train, x_test_rescaled, y_test,
                                     x_grid_rescaled, x_grid, basis_func, lambda_var):
    """
    Computes model weights, predictions, and errors for multiple model orders.

    Parameters:
    ----------
    orders_to_try : list
        List of model orders to evaluate
    x_train_rescaled : numpy.ndarray
        Rescaled training input features
    y_train : numpy.ndarray
        Training target values
    x_test_rescaled : numpy.ndarray
        Rescaled testing input features
    y_test : numpy.ndarray
        Testing target values
    x_grid_rescaled : numpy.ndarray
        Rescaled grid points for visualization
    x_grid : numpy.ndarray
        Original grid points for visualization
    basis_func : callable
        Function to compute basis functions
    lambda_var : float
        Regularization parameter (0 for ML weight estimates)

    Returns:
    -------
    weights_all : numpy.ndarray
        Weights for all model orders
    func_all : numpy.ndarray
        Predictions on grid for all model orders
    train_err : numpy.ndarray
        Training errors for all model orders
    test_err : numpy.ndarray
        Testing errors for all model orders
    """
    K = len(orders_to_try)
    weights_all = np.zeros((max(orders_to_try)+1,K))
    func_all = np.zeros((x_grid.shape[0],K))
    train_err = np.zeros((K,))
    test_err = np.zeros((K,))

    for order_idx in range(K):
        order = orders_to_try[order_idx]
            # Compute the basis function values for training, test, grid data
        phi_train, phi_test, phi_grid, M = generate_basis_matrices(
            x_train_rescaled, x_test_rescaled, x_grid_rescaled, basis_func, order)
        # Compute the weights
        weights = compute_weights(phi_train, y_train, lambda_var)

        weights_all[:M,order_idx] = weights
        # TODO Compute predictions on grid
        # and compute errors


    return weights_all, func_all, train_err, test_err



def compute_errors_across_lambda(phi_train, y_train, phi_test, y_test, x_grid, phi_grid, lambda_var):
    """
    Compute MAP weights, predictions, and errors for different lambda values.

    Args:
        phi_train: Training basis matrix
        y_train: Training targets
        phi_test: Test basis matrix
        y_test: Test targets
        x_grid: Grid points for visualization
        phi_grid: Grid basis matrix for visualization
        lambda_var: Array of regularization parameters (alpha/beta)

    Returns:
        weights_all: MAP weights for each lambda
        func_all: Predictions on grid points for each lambda
        train_err: Training errors for each lambda
        test_err: Test errors for each lambda
    """
    # Compute ML/MAP least squares fit for each model
    # Also compute corresponding training and test accuracy
    K = lambda_var.shape[0]
    M = phi_train.shape[1]
    weights_all = np.zeros((M,K))
    func_all    = np.zeros((x_grid.shape[0],K))
    train_err   = np.zeros((K,))
    test_err    = np.zeros((K,))

    for lambda_idx in range(K):
        # Call the function
        weights = compute_weights(phi_train, y_train, lambda_var[lambda_idx])

        weights_all[:M,lambda_idx] = weights
        # TODO Compute predictions on grid
        # and compute errors

    return weights_all, func_all, train_err, test_err



def generate_posterior_samples(phi_train, y_train, phi_grid, alpha, beta, num_samples=10):
    """
    Generate samples from the Gaussian posterior distribution.

    Args:
        phi_train: Training basis matrix (size NxM)
        y_train: Training target labels (size Nx1)
        phi_grid: Grid basis matrix for visualization (size GxM)
        alpha: Prior inverse-variance hyperparameter alpha (float)
        beta:  Likelihood inverse-variance hyperparameter beta (float)
        num_samples: Number of samples to generate (default: 10)

    Returns:
        func_samples: Matrix of function samples (size G x num_samples)
        post_mean: Posterior mean (size Mx1)
        post_inv_covar: Posterior inverse covariance matrix (size MxM)
    """
    # Calculate posterior inverse covariance and mean
    post_inv_covar = ...
    post_mean = ...

    # Calculate posterior covariance (ensure symmetry for numerical stability)
    post_covar = np.linalg.inv(post_inv_covar)
    post_covar = 0.5 * (post_covar + post_covar.T)

    # Generate samples from posterior (use np.random.multivariate_normal)
    func_samples = ...

    return func_samples, post_mean, post_inv_covar


# Code below demonstrates how to load data, fit a simple model, and plot results.
# You may reuse pieces of this code when answering questions in the PDF handout.
if __name__ == "__main__":
    # Load motorcyle impact data: Xtrain, Ytrain, Xtest, Ytest
    data = np.load("motor.npy", allow_pickle=True).item()
    x_train = data["Xtrain"]
    y_train = data["Ytrain"]
    x_test = data["Xtest"]
    y_test = data["Ytest"]

    # Extract the number of training and test samples
    num_train = x_train.shape[0]
    num_test = x_test.shape[0]

    # Regular grid to use for result plots
    num_grid  = 1000
    x_grid  = np.linspace(0, 60, num_grid)

    # Rescale all inputs so training lies in [-1,+1] for numerical stability
    x_train_rescaled, x_test_rescaled, x_grid_rescaled, _, _ = rescale_inputs(x_train, x_test, x_grid)

    # Example: ML estimation of a two-parameter family: y = w(1) + w(2)*x
    phi_train = basis_poly(x_train_rescaled, 1)
    a = np.matmul(phi_train.T, phi_train)
    b = np.matmul(phi_train.T, y_train)
    weights = np.linalg.lstsq(a, b, rcond=-1)[0] # Used instead of matrix inversion for numerical stability

    # Predictions of ML estimate at training and test points, and on dense grid
    yhat_train = phi_train * weights

    phi_test = basis_poly(x_test_rescaled, 1)
    yhat_test = np.matmul(phi_test, weights)

    phi_grid = basis_poly(x_grid_rescaled, 1)
    yhat_grid = np.matmul(phi_grid, weights)

    # Squared error metric on training and test data for this function
    train_err = np.sqrt(np.mean((y_train - np.matmul(phi_train,weights))**2))
    test_err  = np.sqrt(np.mean((y_test - np.matmul(phi_test,weights))**2))

    # Plot ML estimate on dense grid, along with training data
    plt.plot(x_train, y_train, '.k')
    plt.plot(x_grid, yhat_grid, '-b')
    plt.show()

