# Python program to demonstrate
# command line arguments
import argparse

parser = argparse.ArgumentParser()

parser.add_argument("-o", "--Output", help="Model output path")
parser.add_argument("-i", "--Input", help="Training dataset input pattern")

# Read arguments from command line
args = parser.parse_args()

if args.Output:
    print("Output as: % s" % args.Output)

if args.Input:
    print("Input as: % s" % args.Input)
