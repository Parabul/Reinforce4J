��	
��
D
AddV2
x"T
y"T
z"T"
Ttype:
2	��
^
AssignVariableOp
resource
value"dtype"
dtypetype"
validate_shapebool( �
�
BiasAdd

value"T	
bias"T
output"T""
Ttype:
2	"-
data_formatstringNHWC:
NHWCNCHW
8
Const
output"dtype"
valuetensor"
dtypetype
�
Conv2D

input"T
filter"T
output"T"
Ttype:	
2"
strides	list(int)"
use_cudnn_on_gpubool(",
paddingstring:
SAMEVALIDEXPLICIT""
explicit_paddings	list(int)
 "-
data_formatstringNHWC:
NHWCNCHW" 
	dilations	list(int)

$
DisableCopyOnRead
resource�
.
Identity

input"T
output"T"	
Ttype
�
MatMul
a"T
b"T
product"T"
transpose_abool( "
transpose_bbool( "
Ttype:
2	"
grad_abool( "
grad_bbool( 
�
MergeV2Checkpoints
checkpoint_prefixes
destination_prefix"
delete_old_dirsbool("
allow_missing_filesbool( �

NoOp
M
Pack
values"T*N
output"T"
Nint(0"	
Ttype"
axisint 
C
Placeholder
output"dtype"
dtypetype"
shapeshape:
@
ReadVariableOp
resource
value"dtype"
dtypetype�
E
Relu
features"T
activations"T"
Ttype:
2	
[
Reshape
tensor"T
shape"Tshape
output"T"	
Ttype"
Tshapetype0:
2	
o
	RestoreV2

prefix
tensor_names
shape_and_slices
tensors2dtypes"
dtypes
list(type)(0�
l
SaveV2

prefix
tensor_names
shape_and_slices
tensors2dtypes"
dtypes
list(type)(0�
?
Select
	condition

t"T
e"T
output"T"	
Ttype
d
Shape

input"T&
output"out_type��out_type"	
Ttype"
out_typetype0:
2	
H
ShardedFilename
basename	
shard

num_shards
filename
9
Softmax
logits"T
softmax"T"
Ttype:
2
N
Squeeze

input"T
output"T"	
Ttype"
squeeze_dims	list(int)
 (
�
StatefulPartitionedCall
args2Tin
output2Tout"
Tin
list(type)("
Tout
list(type)("	
ffunc"
configstring "
config_protostring "
executor_typestring ��
@
StaticRegexFullMatch	
input

output
"
patternstring
�
StridedSlice

input"T
begin"Index
end"Index
strides"Index
output"T"	
Ttype"
Indextype:
2	"

begin_maskint "
end_maskint "
ellipsis_maskint "
new_axis_maskint "
shrink_axis_maskint 
L

StringJoin
inputs*N

output"

Nint("
	separatorstring 
-
Tanh
x"T
y"T"
Ttype:

2
�
VarHandleOp
resource"
	containerstring "
shared_namestring "

debug_namestring "
dtypetype"
shapeshape"#
allowed_deviceslist(string)
 �
9
VarIsInitializedOp
resource
is_initialized
�"serve*2.18.02v2.18.0-rc2-4-g6550e4bd8028��
�
conv2d_8/kernelVarHandleOp*
_output_shapes
: * 

debug_nameconv2d_8/kernel/*
dtype0*
shape: * 
shared_nameconv2d_8/kernel
{
#conv2d_8/kernel/Read/ReadVariableOpReadVariableOpconv2d_8/kernel*&
_output_shapes
: *
dtype0
�
conv2d_4/kernelVarHandleOp*
_output_shapes
: * 

debug_nameconv2d_4/kernel/*
dtype0*
shape:  * 
shared_nameconv2d_4/kernel
{
#conv2d_4/kernel/Read/ReadVariableOpReadVariableOpconv2d_4/kernel*&
_output_shapes
:  *
dtype0
�
conv2d_3/kernelVarHandleOp*
_output_shapes
: * 

debug_nameconv2d_3/kernel/*
dtype0*
shape:  * 
shared_nameconv2d_3/kernel
{
#conv2d_3/kernel/Read/ReadVariableOpReadVariableOpconv2d_3/kernel*&
_output_shapes
:  *
dtype0
�
policy_output/kernelVarHandleOp*
_output_shapes
: *%

debug_namepolicy_output/kernel/*
dtype0*
shape:	�*%
shared_namepolicy_output/kernel
~
(policy_output/kernel/Read/ReadVariableOpReadVariableOppolicy_output/kernel*
_output_shapes
:	�*
dtype0
�
conv2d_7/biasVarHandleOp*
_output_shapes
: *

debug_nameconv2d_7/bias/*
dtype0*
shape:*
shared_nameconv2d_7/bias
k
!conv2d_7/bias/Read/ReadVariableOpReadVariableOpconv2d_7/bias*
_output_shapes
:*
dtype0
�
conv2d_7/kernelVarHandleOp*
_output_shapes
: * 

debug_nameconv2d_7/kernel/*
dtype0*
shape: * 
shared_nameconv2d_7/kernel
{
#conv2d_7/kernel/Read/ReadVariableOpReadVariableOpconv2d_7/kernel*&
_output_shapes
: *
dtype0
�
conv2d_6/biasVarHandleOp*
_output_shapes
: *

debug_nameconv2d_6/bias/*
dtype0*
shape: *
shared_nameconv2d_6/bias
k
!conv2d_6/bias/Read/ReadVariableOpReadVariableOpconv2d_6/bias*
_output_shapes
: *
dtype0
�
conv2d_5/biasVarHandleOp*
_output_shapes
: *

debug_nameconv2d_5/bias/*
dtype0*
shape: *
shared_nameconv2d_5/bias
k
!conv2d_5/bias/Read/ReadVariableOpReadVariableOpconv2d_5/bias*
_output_shapes
: *
dtype0
�
conv2d_6/kernelVarHandleOp*
_output_shapes
: * 

debug_nameconv2d_6/kernel/*
dtype0*
shape:  * 
shared_nameconv2d_6/kernel
{
#conv2d_6/kernel/Read/ReadVariableOpReadVariableOpconv2d_6/kernel*&
_output_shapes
:  *
dtype0
�
conv2d_5/kernelVarHandleOp*
_output_shapes
: * 

debug_nameconv2d_5/kernel/*
dtype0*
shape:  * 
shared_nameconv2d_5/kernel
{
#conv2d_5/kernel/Read/ReadVariableOpReadVariableOpconv2d_5/kernel*&
_output_shapes
:  *
dtype0
�
conv2d_2/biasVarHandleOp*
_output_shapes
: *

debug_nameconv2d_2/bias/*
dtype0*
shape: *
shared_nameconv2d_2/bias
k
!conv2d_2/bias/Read/ReadVariableOpReadVariableOpconv2d_2/bias*
_output_shapes
: *
dtype0
�
conv2d/kernelVarHandleOp*
_output_shapes
: *

debug_nameconv2d/kernel/*
dtype0*
shape: *
shared_nameconv2d/kernel
w
!conv2d/kernel/Read/ReadVariableOpReadVariableOpconv2d/kernel*&
_output_shapes
: *
dtype0
�
value_output/kernelVarHandleOp*
_output_shapes
: *$

debug_namevalue_output/kernel/*
dtype0*
shape
:*$
shared_namevalue_output/kernel
{
'value_output/kernel/Read/ReadVariableOpReadVariableOpvalue_output/kernel*
_output_shapes

:*
dtype0
�
policy_output/biasVarHandleOp*
_output_shapes
: *#

debug_namepolicy_output/bias/*
dtype0*
shape:*#
shared_namepolicy_output/bias
u
&policy_output/bias/Read/ReadVariableOpReadVariableOppolicy_output/bias*
_output_shapes
:*
dtype0
�
conv2d/biasVarHandleOp*
_output_shapes
: *

debug_nameconv2d/bias/*
dtype0*
shape: *
shared_nameconv2d/bias
g
conv2d/bias/Read/ReadVariableOpReadVariableOpconv2d/bias*
_output_shapes
: *
dtype0
�
conv2d_2/kernelVarHandleOp*
_output_shapes
: * 

debug_nameconv2d_2/kernel/*
dtype0*
shape:  * 
shared_nameconv2d_2/kernel
{
#conv2d_2/kernel/Read/ReadVariableOpReadVariableOpconv2d_2/kernel*&
_output_shapes
:  *
dtype0
�

dense/biasVarHandleOp*
_output_shapes
: *

debug_namedense/bias/*
dtype0*
shape:�*
shared_name
dense/bias
f
dense/bias/Read/ReadVariableOpReadVariableOp
dense/bias*
_output_shapes	
:�*
dtype0
�
conv2d_1/biasVarHandleOp*
_output_shapes
: *

debug_nameconv2d_1/bias/*
dtype0*
shape: *
shared_nameconv2d_1/bias
k
!conv2d_1/bias/Read/ReadVariableOpReadVariableOpconv2d_1/bias*
_output_shapes
: *
dtype0
�
value_output/biasVarHandleOp*
_output_shapes
: *"

debug_namevalue_output/bias/*
dtype0*
shape:*"
shared_namevalue_output/bias
s
%value_output/bias/Read/ReadVariableOpReadVariableOpvalue_output/bias*
_output_shapes
:*
dtype0
�
dense/kernelVarHandleOp*
_output_shapes
: *

debug_namedense/kernel/*
dtype0*
shape:	�*
shared_namedense/kernel
n
 dense/kernel/Read/ReadVariableOpReadVariableOpdense/kernel*
_output_shapes
:	�*
dtype0
�
conv2d_8/biasVarHandleOp*
_output_shapes
: *

debug_nameconv2d_8/bias/*
dtype0*
shape:*
shared_nameconv2d_8/bias
k
!conv2d_8/bias/Read/ReadVariableOpReadVariableOpconv2d_8/bias*
_output_shapes
:*
dtype0
�
conv2d_4/biasVarHandleOp*
_output_shapes
: *

debug_nameconv2d_4/bias/*
dtype0*
shape: *
shared_nameconv2d_4/bias
k
!conv2d_4/bias/Read/ReadVariableOpReadVariableOpconv2d_4/bias*
_output_shapes
: *
dtype0
�
conv2d_3/biasVarHandleOp*
_output_shapes
: *

debug_nameconv2d_3/bias/*
dtype0*
shape: *
shared_nameconv2d_3/bias
k
!conv2d_3/bias/Read/ReadVariableOpReadVariableOpconv2d_3/bias*
_output_shapes
: *
dtype0
�
conv2d_1/kernelVarHandleOp*
_output_shapes
: * 

debug_nameconv2d_1/kernel/*
dtype0*
shape:  * 
shared_nameconv2d_1/kernel
{
#conv2d_1/kernel/Read/ReadVariableOpReadVariableOpconv2d_1/kernel*&
_output_shapes
:  *
dtype0
�
policy_output/bias_1VarHandleOp*
_output_shapes
: *%

debug_namepolicy_output/bias_1/*
dtype0*
shape:*%
shared_namepolicy_output/bias_1
y
(policy_output/bias_1/Read/ReadVariableOpReadVariableOppolicy_output/bias_1*
_output_shapes
:*
dtype0
�
#Variable/Initializer/ReadVariableOpReadVariableOppolicy_output/bias_1*
_class
loc:@Variable*
_output_shapes
:*
dtype0
�
VariableVarHandleOp*
_class
loc:@Variable*
_output_shapes
: *

debug_name	Variable/*
dtype0*
shape:*
shared_name
Variable
a
)Variable/IsInitialized/VarIsInitializedOpVarIsInitializedOpVariable*
_output_shapes
: 
_
Variable/AssignAssignVariableOpVariable#Variable/Initializer/ReadVariableOp*
dtype0
a
Variable/Read/ReadVariableOpReadVariableOpVariable*
_output_shapes
:*
dtype0
�
policy_output/kernel_1VarHandleOp*
_output_shapes
: *'

debug_namepolicy_output/kernel_1/*
dtype0*
shape:	�*'
shared_namepolicy_output/kernel_1
�
*policy_output/kernel_1/Read/ReadVariableOpReadVariableOppolicy_output/kernel_1*
_output_shapes
:	�*
dtype0
�
%Variable_1/Initializer/ReadVariableOpReadVariableOppolicy_output/kernel_1*
_class
loc:@Variable_1*
_output_shapes
:	�*
dtype0
�

Variable_1VarHandleOp*
_class
loc:@Variable_1*
_output_shapes
: *

debug_nameVariable_1/*
dtype0*
shape:	�*
shared_name
Variable_1
e
+Variable_1/IsInitialized/VarIsInitializedOpVarIsInitializedOp
Variable_1*
_output_shapes
: 
e
Variable_1/AssignAssignVariableOp
Variable_1%Variable_1/Initializer/ReadVariableOp*
dtype0
j
Variable_1/Read/ReadVariableOpReadVariableOp
Variable_1*
_output_shapes
:	�*
dtype0
�
value_output/bias_1VarHandleOp*
_output_shapes
: *$

debug_namevalue_output/bias_1/*
dtype0*
shape:*$
shared_namevalue_output/bias_1
w
'value_output/bias_1/Read/ReadVariableOpReadVariableOpvalue_output/bias_1*
_output_shapes
:*
dtype0
�
%Variable_2/Initializer/ReadVariableOpReadVariableOpvalue_output/bias_1*
_class
loc:@Variable_2*
_output_shapes
:*
dtype0
�

Variable_2VarHandleOp*
_class
loc:@Variable_2*
_output_shapes
: *

debug_nameVariable_2/*
dtype0*
shape:*
shared_name
Variable_2
e
+Variable_2/IsInitialized/VarIsInitializedOpVarIsInitializedOp
Variable_2*
_output_shapes
: 
e
Variable_2/AssignAssignVariableOp
Variable_2%Variable_2/Initializer/ReadVariableOp*
dtype0
e
Variable_2/Read/ReadVariableOpReadVariableOp
Variable_2*
_output_shapes
:*
dtype0
�
value_output/kernel_1VarHandleOp*
_output_shapes
: *&

debug_namevalue_output/kernel_1/*
dtype0*
shape
:*&
shared_namevalue_output/kernel_1

)value_output/kernel_1/Read/ReadVariableOpReadVariableOpvalue_output/kernel_1*
_output_shapes

:*
dtype0
�
%Variable_3/Initializer/ReadVariableOpReadVariableOpvalue_output/kernel_1*
_class
loc:@Variable_3*
_output_shapes

:*
dtype0
�

Variable_3VarHandleOp*
_class
loc:@Variable_3*
_output_shapes
: *

debug_nameVariable_3/*
dtype0*
shape
:*
shared_name
Variable_3
e
+Variable_3/IsInitialized/VarIsInitializedOpVarIsInitializedOp
Variable_3*
_output_shapes
: 
e
Variable_3/AssignAssignVariableOp
Variable_3%Variable_3/Initializer/ReadVariableOp*
dtype0
i
Variable_3/Read/ReadVariableOpReadVariableOp
Variable_3*
_output_shapes

:*
dtype0
�
dense/bias_1VarHandleOp*
_output_shapes
: *

debug_namedense/bias_1/*
dtype0*
shape:�*
shared_namedense/bias_1
j
 dense/bias_1/Read/ReadVariableOpReadVariableOpdense/bias_1*
_output_shapes	
:�*
dtype0
�
%Variable_4/Initializer/ReadVariableOpReadVariableOpdense/bias_1*
_class
loc:@Variable_4*
_output_shapes	
:�*
dtype0
�

Variable_4VarHandleOp*
_class
loc:@Variable_4*
_output_shapes
: *

debug_nameVariable_4/*
dtype0*
shape:�*
shared_name
Variable_4
e
+Variable_4/IsInitialized/VarIsInitializedOpVarIsInitializedOp
Variable_4*
_output_shapes
: 
e
Variable_4/AssignAssignVariableOp
Variable_4%Variable_4/Initializer/ReadVariableOp*
dtype0
f
Variable_4/Read/ReadVariableOpReadVariableOp
Variable_4*
_output_shapes	
:�*
dtype0
�
dense/kernel_1VarHandleOp*
_output_shapes
: *

debug_namedense/kernel_1/*
dtype0*
shape:	�*
shared_namedense/kernel_1
r
"dense/kernel_1/Read/ReadVariableOpReadVariableOpdense/kernel_1*
_output_shapes
:	�*
dtype0
�
%Variable_5/Initializer/ReadVariableOpReadVariableOpdense/kernel_1*
_class
loc:@Variable_5*
_output_shapes
:	�*
dtype0
�

Variable_5VarHandleOp*
_class
loc:@Variable_5*
_output_shapes
: *

debug_nameVariable_5/*
dtype0*
shape:	�*
shared_name
Variable_5
e
+Variable_5/IsInitialized/VarIsInitializedOpVarIsInitializedOp
Variable_5*
_output_shapes
: 
e
Variable_5/AssignAssignVariableOp
Variable_5%Variable_5/Initializer/ReadVariableOp*
dtype0
j
Variable_5/Read/ReadVariableOpReadVariableOp
Variable_5*
_output_shapes
:	�*
dtype0
�
conv2d_7/bias_1VarHandleOp*
_output_shapes
: * 

debug_nameconv2d_7/bias_1/*
dtype0*
shape:* 
shared_nameconv2d_7/bias_1
o
#conv2d_7/bias_1/Read/ReadVariableOpReadVariableOpconv2d_7/bias_1*
_output_shapes
:*
dtype0
�
%Variable_6/Initializer/ReadVariableOpReadVariableOpconv2d_7/bias_1*
_class
loc:@Variable_6*
_output_shapes
:*
dtype0
�

Variable_6VarHandleOp*
_class
loc:@Variable_6*
_output_shapes
: *

debug_nameVariable_6/*
dtype0*
shape:*
shared_name
Variable_6
e
+Variable_6/IsInitialized/VarIsInitializedOpVarIsInitializedOp
Variable_6*
_output_shapes
: 
e
Variable_6/AssignAssignVariableOp
Variable_6%Variable_6/Initializer/ReadVariableOp*
dtype0
e
Variable_6/Read/ReadVariableOpReadVariableOp
Variable_6*
_output_shapes
:*
dtype0
�
conv2d_7/kernel_1VarHandleOp*
_output_shapes
: *"

debug_nameconv2d_7/kernel_1/*
dtype0*
shape: *"
shared_nameconv2d_7/kernel_1

%conv2d_7/kernel_1/Read/ReadVariableOpReadVariableOpconv2d_7/kernel_1*&
_output_shapes
: *
dtype0
�
%Variable_7/Initializer/ReadVariableOpReadVariableOpconv2d_7/kernel_1*
_class
loc:@Variable_7*&
_output_shapes
: *
dtype0
�

Variable_7VarHandleOp*
_class
loc:@Variable_7*
_output_shapes
: *

debug_nameVariable_7/*
dtype0*
shape: *
shared_name
Variable_7
e
+Variable_7/IsInitialized/VarIsInitializedOpVarIsInitializedOp
Variable_7*
_output_shapes
: 
e
Variable_7/AssignAssignVariableOp
Variable_7%Variable_7/Initializer/ReadVariableOp*
dtype0
q
Variable_7/Read/ReadVariableOpReadVariableOp
Variable_7*&
_output_shapes
: *
dtype0
�
conv2d_8/bias_1VarHandleOp*
_output_shapes
: * 

debug_nameconv2d_8/bias_1/*
dtype0*
shape:* 
shared_nameconv2d_8/bias_1
o
#conv2d_8/bias_1/Read/ReadVariableOpReadVariableOpconv2d_8/bias_1*
_output_shapes
:*
dtype0
�
%Variable_8/Initializer/ReadVariableOpReadVariableOpconv2d_8/bias_1*
_class
loc:@Variable_8*
_output_shapes
:*
dtype0
�

Variable_8VarHandleOp*
_class
loc:@Variable_8*
_output_shapes
: *

debug_nameVariable_8/*
dtype0*
shape:*
shared_name
Variable_8
e
+Variable_8/IsInitialized/VarIsInitializedOpVarIsInitializedOp
Variable_8*
_output_shapes
: 
e
Variable_8/AssignAssignVariableOp
Variable_8%Variable_8/Initializer/ReadVariableOp*
dtype0
e
Variable_8/Read/ReadVariableOpReadVariableOp
Variable_8*
_output_shapes
:*
dtype0
�
conv2d_8/kernel_1VarHandleOp*
_output_shapes
: *"

debug_nameconv2d_8/kernel_1/*
dtype0*
shape: *"
shared_nameconv2d_8/kernel_1

%conv2d_8/kernel_1/Read/ReadVariableOpReadVariableOpconv2d_8/kernel_1*&
_output_shapes
: *
dtype0
�
%Variable_9/Initializer/ReadVariableOpReadVariableOpconv2d_8/kernel_1*
_class
loc:@Variable_9*&
_output_shapes
: *
dtype0
�

Variable_9VarHandleOp*
_class
loc:@Variable_9*
_output_shapes
: *

debug_nameVariable_9/*
dtype0*
shape: *
shared_name
Variable_9
e
+Variable_9/IsInitialized/VarIsInitializedOpVarIsInitializedOp
Variable_9*
_output_shapes
: 
e
Variable_9/AssignAssignVariableOp
Variable_9%Variable_9/Initializer/ReadVariableOp*
dtype0
q
Variable_9/Read/ReadVariableOpReadVariableOp
Variable_9*&
_output_shapes
: *
dtype0
�
conv2d_6/bias_1VarHandleOp*
_output_shapes
: * 

debug_nameconv2d_6/bias_1/*
dtype0*
shape: * 
shared_nameconv2d_6/bias_1
o
#conv2d_6/bias_1/Read/ReadVariableOpReadVariableOpconv2d_6/bias_1*
_output_shapes
: *
dtype0
�
&Variable_10/Initializer/ReadVariableOpReadVariableOpconv2d_6/bias_1*
_class
loc:@Variable_10*
_output_shapes
: *
dtype0
�
Variable_10VarHandleOp*
_class
loc:@Variable_10*
_output_shapes
: *

debug_nameVariable_10/*
dtype0*
shape: *
shared_nameVariable_10
g
,Variable_10/IsInitialized/VarIsInitializedOpVarIsInitializedOpVariable_10*
_output_shapes
: 
h
Variable_10/AssignAssignVariableOpVariable_10&Variable_10/Initializer/ReadVariableOp*
dtype0
g
Variable_10/Read/ReadVariableOpReadVariableOpVariable_10*
_output_shapes
: *
dtype0
�
conv2d_6/kernel_1VarHandleOp*
_output_shapes
: *"

debug_nameconv2d_6/kernel_1/*
dtype0*
shape:  *"
shared_nameconv2d_6/kernel_1

%conv2d_6/kernel_1/Read/ReadVariableOpReadVariableOpconv2d_6/kernel_1*&
_output_shapes
:  *
dtype0
�
&Variable_11/Initializer/ReadVariableOpReadVariableOpconv2d_6/kernel_1*
_class
loc:@Variable_11*&
_output_shapes
:  *
dtype0
�
Variable_11VarHandleOp*
_class
loc:@Variable_11*
_output_shapes
: *

debug_nameVariable_11/*
dtype0*
shape:  *
shared_nameVariable_11
g
,Variable_11/IsInitialized/VarIsInitializedOpVarIsInitializedOpVariable_11*
_output_shapes
: 
h
Variable_11/AssignAssignVariableOpVariable_11&Variable_11/Initializer/ReadVariableOp*
dtype0
s
Variable_11/Read/ReadVariableOpReadVariableOpVariable_11*&
_output_shapes
:  *
dtype0
�
conv2d_5/bias_1VarHandleOp*
_output_shapes
: * 

debug_nameconv2d_5/bias_1/*
dtype0*
shape: * 
shared_nameconv2d_5/bias_1
o
#conv2d_5/bias_1/Read/ReadVariableOpReadVariableOpconv2d_5/bias_1*
_output_shapes
: *
dtype0
�
&Variable_12/Initializer/ReadVariableOpReadVariableOpconv2d_5/bias_1*
_class
loc:@Variable_12*
_output_shapes
: *
dtype0
�
Variable_12VarHandleOp*
_class
loc:@Variable_12*
_output_shapes
: *

debug_nameVariable_12/*
dtype0*
shape: *
shared_nameVariable_12
g
,Variable_12/IsInitialized/VarIsInitializedOpVarIsInitializedOpVariable_12*
_output_shapes
: 
h
Variable_12/AssignAssignVariableOpVariable_12&Variable_12/Initializer/ReadVariableOp*
dtype0
g
Variable_12/Read/ReadVariableOpReadVariableOpVariable_12*
_output_shapes
: *
dtype0
�
conv2d_5/kernel_1VarHandleOp*
_output_shapes
: *"

debug_nameconv2d_5/kernel_1/*
dtype0*
shape:  *"
shared_nameconv2d_5/kernel_1

%conv2d_5/kernel_1/Read/ReadVariableOpReadVariableOpconv2d_5/kernel_1*&
_output_shapes
:  *
dtype0
�
&Variable_13/Initializer/ReadVariableOpReadVariableOpconv2d_5/kernel_1*
_class
loc:@Variable_13*&
_output_shapes
:  *
dtype0
�
Variable_13VarHandleOp*
_class
loc:@Variable_13*
_output_shapes
: *

debug_nameVariable_13/*
dtype0*
shape:  *
shared_nameVariable_13
g
,Variable_13/IsInitialized/VarIsInitializedOpVarIsInitializedOpVariable_13*
_output_shapes
: 
h
Variable_13/AssignAssignVariableOpVariable_13&Variable_13/Initializer/ReadVariableOp*
dtype0
s
Variable_13/Read/ReadVariableOpReadVariableOpVariable_13*&
_output_shapes
:  *
dtype0
�
conv2d_4/bias_1VarHandleOp*
_output_shapes
: * 

debug_nameconv2d_4/bias_1/*
dtype0*
shape: * 
shared_nameconv2d_4/bias_1
o
#conv2d_4/bias_1/Read/ReadVariableOpReadVariableOpconv2d_4/bias_1*
_output_shapes
: *
dtype0
�
&Variable_14/Initializer/ReadVariableOpReadVariableOpconv2d_4/bias_1*
_class
loc:@Variable_14*
_output_shapes
: *
dtype0
�
Variable_14VarHandleOp*
_class
loc:@Variable_14*
_output_shapes
: *

debug_nameVariable_14/*
dtype0*
shape: *
shared_nameVariable_14
g
,Variable_14/IsInitialized/VarIsInitializedOpVarIsInitializedOpVariable_14*
_output_shapes
: 
h
Variable_14/AssignAssignVariableOpVariable_14&Variable_14/Initializer/ReadVariableOp*
dtype0
g
Variable_14/Read/ReadVariableOpReadVariableOpVariable_14*
_output_shapes
: *
dtype0
�
conv2d_4/kernel_1VarHandleOp*
_output_shapes
: *"

debug_nameconv2d_4/kernel_1/*
dtype0*
shape:  *"
shared_nameconv2d_4/kernel_1

%conv2d_4/kernel_1/Read/ReadVariableOpReadVariableOpconv2d_4/kernel_1*&
_output_shapes
:  *
dtype0
�
&Variable_15/Initializer/ReadVariableOpReadVariableOpconv2d_4/kernel_1*
_class
loc:@Variable_15*&
_output_shapes
:  *
dtype0
�
Variable_15VarHandleOp*
_class
loc:@Variable_15*
_output_shapes
: *

debug_nameVariable_15/*
dtype0*
shape:  *
shared_nameVariable_15
g
,Variable_15/IsInitialized/VarIsInitializedOpVarIsInitializedOpVariable_15*
_output_shapes
: 
h
Variable_15/AssignAssignVariableOpVariable_15&Variable_15/Initializer/ReadVariableOp*
dtype0
s
Variable_15/Read/ReadVariableOpReadVariableOpVariable_15*&
_output_shapes
:  *
dtype0
�
conv2d_3/bias_1VarHandleOp*
_output_shapes
: * 

debug_nameconv2d_3/bias_1/*
dtype0*
shape: * 
shared_nameconv2d_3/bias_1
o
#conv2d_3/bias_1/Read/ReadVariableOpReadVariableOpconv2d_3/bias_1*
_output_shapes
: *
dtype0
�
&Variable_16/Initializer/ReadVariableOpReadVariableOpconv2d_3/bias_1*
_class
loc:@Variable_16*
_output_shapes
: *
dtype0
�
Variable_16VarHandleOp*
_class
loc:@Variable_16*
_output_shapes
: *

debug_nameVariable_16/*
dtype0*
shape: *
shared_nameVariable_16
g
,Variable_16/IsInitialized/VarIsInitializedOpVarIsInitializedOpVariable_16*
_output_shapes
: 
h
Variable_16/AssignAssignVariableOpVariable_16&Variable_16/Initializer/ReadVariableOp*
dtype0
g
Variable_16/Read/ReadVariableOpReadVariableOpVariable_16*
_output_shapes
: *
dtype0
�
conv2d_3/kernel_1VarHandleOp*
_output_shapes
: *"

debug_nameconv2d_3/kernel_1/*
dtype0*
shape:  *"
shared_nameconv2d_3/kernel_1

%conv2d_3/kernel_1/Read/ReadVariableOpReadVariableOpconv2d_3/kernel_1*&
_output_shapes
:  *
dtype0
�
&Variable_17/Initializer/ReadVariableOpReadVariableOpconv2d_3/kernel_1*
_class
loc:@Variable_17*&
_output_shapes
:  *
dtype0
�
Variable_17VarHandleOp*
_class
loc:@Variable_17*
_output_shapes
: *

debug_nameVariable_17/*
dtype0*
shape:  *
shared_nameVariable_17
g
,Variable_17/IsInitialized/VarIsInitializedOpVarIsInitializedOpVariable_17*
_output_shapes
: 
h
Variable_17/AssignAssignVariableOpVariable_17&Variable_17/Initializer/ReadVariableOp*
dtype0
s
Variable_17/Read/ReadVariableOpReadVariableOpVariable_17*&
_output_shapes
:  *
dtype0
�
conv2d_2/bias_1VarHandleOp*
_output_shapes
: * 

debug_nameconv2d_2/bias_1/*
dtype0*
shape: * 
shared_nameconv2d_2/bias_1
o
#conv2d_2/bias_1/Read/ReadVariableOpReadVariableOpconv2d_2/bias_1*
_output_shapes
: *
dtype0
�
&Variable_18/Initializer/ReadVariableOpReadVariableOpconv2d_2/bias_1*
_class
loc:@Variable_18*
_output_shapes
: *
dtype0
�
Variable_18VarHandleOp*
_class
loc:@Variable_18*
_output_shapes
: *

debug_nameVariable_18/*
dtype0*
shape: *
shared_nameVariable_18
g
,Variable_18/IsInitialized/VarIsInitializedOpVarIsInitializedOpVariable_18*
_output_shapes
: 
h
Variable_18/AssignAssignVariableOpVariable_18&Variable_18/Initializer/ReadVariableOp*
dtype0
g
Variable_18/Read/ReadVariableOpReadVariableOpVariable_18*
_output_shapes
: *
dtype0
�
conv2d_2/kernel_1VarHandleOp*
_output_shapes
: *"

debug_nameconv2d_2/kernel_1/*
dtype0*
shape:  *"
shared_nameconv2d_2/kernel_1

%conv2d_2/kernel_1/Read/ReadVariableOpReadVariableOpconv2d_2/kernel_1*&
_output_shapes
:  *
dtype0
�
&Variable_19/Initializer/ReadVariableOpReadVariableOpconv2d_2/kernel_1*
_class
loc:@Variable_19*&
_output_shapes
:  *
dtype0
�
Variable_19VarHandleOp*
_class
loc:@Variable_19*
_output_shapes
: *

debug_nameVariable_19/*
dtype0*
shape:  *
shared_nameVariable_19
g
,Variable_19/IsInitialized/VarIsInitializedOpVarIsInitializedOpVariable_19*
_output_shapes
: 
h
Variable_19/AssignAssignVariableOpVariable_19&Variable_19/Initializer/ReadVariableOp*
dtype0
s
Variable_19/Read/ReadVariableOpReadVariableOpVariable_19*&
_output_shapes
:  *
dtype0
�
conv2d_1/bias_1VarHandleOp*
_output_shapes
: * 

debug_nameconv2d_1/bias_1/*
dtype0*
shape: * 
shared_nameconv2d_1/bias_1
o
#conv2d_1/bias_1/Read/ReadVariableOpReadVariableOpconv2d_1/bias_1*
_output_shapes
: *
dtype0
�
&Variable_20/Initializer/ReadVariableOpReadVariableOpconv2d_1/bias_1*
_class
loc:@Variable_20*
_output_shapes
: *
dtype0
�
Variable_20VarHandleOp*
_class
loc:@Variable_20*
_output_shapes
: *

debug_nameVariable_20/*
dtype0*
shape: *
shared_nameVariable_20
g
,Variable_20/IsInitialized/VarIsInitializedOpVarIsInitializedOpVariable_20*
_output_shapes
: 
h
Variable_20/AssignAssignVariableOpVariable_20&Variable_20/Initializer/ReadVariableOp*
dtype0
g
Variable_20/Read/ReadVariableOpReadVariableOpVariable_20*
_output_shapes
: *
dtype0
�
conv2d_1/kernel_1VarHandleOp*
_output_shapes
: *"

debug_nameconv2d_1/kernel_1/*
dtype0*
shape:  *"
shared_nameconv2d_1/kernel_1

%conv2d_1/kernel_1/Read/ReadVariableOpReadVariableOpconv2d_1/kernel_1*&
_output_shapes
:  *
dtype0
�
&Variable_21/Initializer/ReadVariableOpReadVariableOpconv2d_1/kernel_1*
_class
loc:@Variable_21*&
_output_shapes
:  *
dtype0
�
Variable_21VarHandleOp*
_class
loc:@Variable_21*
_output_shapes
: *

debug_nameVariable_21/*
dtype0*
shape:  *
shared_nameVariable_21
g
,Variable_21/IsInitialized/VarIsInitializedOpVarIsInitializedOpVariable_21*
_output_shapes
: 
h
Variable_21/AssignAssignVariableOpVariable_21&Variable_21/Initializer/ReadVariableOp*
dtype0
s
Variable_21/Read/ReadVariableOpReadVariableOpVariable_21*&
_output_shapes
:  *
dtype0
�
conv2d/bias_1VarHandleOp*
_output_shapes
: *

debug_nameconv2d/bias_1/*
dtype0*
shape: *
shared_nameconv2d/bias_1
k
!conv2d/bias_1/Read/ReadVariableOpReadVariableOpconv2d/bias_1*
_output_shapes
: *
dtype0
�
&Variable_22/Initializer/ReadVariableOpReadVariableOpconv2d/bias_1*
_class
loc:@Variable_22*
_output_shapes
: *
dtype0
�
Variable_22VarHandleOp*
_class
loc:@Variable_22*
_output_shapes
: *

debug_nameVariable_22/*
dtype0*
shape: *
shared_nameVariable_22
g
,Variable_22/IsInitialized/VarIsInitializedOpVarIsInitializedOpVariable_22*
_output_shapes
: 
h
Variable_22/AssignAssignVariableOpVariable_22&Variable_22/Initializer/ReadVariableOp*
dtype0
g
Variable_22/Read/ReadVariableOpReadVariableOpVariable_22*
_output_shapes
: *
dtype0
�
conv2d/kernel_1VarHandleOp*
_output_shapes
: * 

debug_nameconv2d/kernel_1/*
dtype0*
shape: * 
shared_nameconv2d/kernel_1
{
#conv2d/kernel_1/Read/ReadVariableOpReadVariableOpconv2d/kernel_1*&
_output_shapes
: *
dtype0
�
&Variable_23/Initializer/ReadVariableOpReadVariableOpconv2d/kernel_1*
_class
loc:@Variable_23*&
_output_shapes
: *
dtype0
�
Variable_23VarHandleOp*
_class
loc:@Variable_23*
_output_shapes
: *

debug_nameVariable_23/*
dtype0*
shape: *
shared_nameVariable_23
g
,Variable_23/IsInitialized/VarIsInitializedOpVarIsInitializedOpVariable_23*
_output_shapes
: 
h
Variable_23/AssignAssignVariableOpVariable_23&Variable_23/Initializer/ReadVariableOp*
dtype0
s
Variable_23/Read/ReadVariableOpReadVariableOpVariable_23*&
_output_shapes
: *
dtype0
p
serve_input_1Placeholder*'
_output_shapes
:���������**
dtype0*
shape:���������*
�
StatefulPartitionedCallStatefulPartitionedCallserve_input_1conv2d/kernel_1conv2d/bias_1conv2d_1/kernel_1conv2d_1/bias_1conv2d_2/kernel_1conv2d_2/bias_1conv2d_3/kernel_1conv2d_3/bias_1conv2d_4/kernel_1conv2d_4/bias_1conv2d_5/kernel_1conv2d_5/bias_1conv2d_6/kernel_1conv2d_6/bias_1conv2d_8/kernel_1conv2d_8/bias_1conv2d_7/kernel_1conv2d_7/bias_1dense/kernel_1dense/bias_1policy_output/kernel_1policy_output/bias_1value_output/kernel_1value_output/bias_1*$
Tin
2*
Tout
2*
_collective_manager_ids
 *:
_output_shapes(
&:���������:���������*:
_read_only_resource_inputs
	
*5
config_proto%#

CPU

GPU2*0J 8� �J *7
f2R0
.__inference_signature_wrapper___call___2234724
z
serving_default_input_1Placeholder*'
_output_shapes
:���������**
dtype0*
shape:���������*
�
StatefulPartitionedCall_1StatefulPartitionedCallserving_default_input_1conv2d/kernel_1conv2d/bias_1conv2d_1/kernel_1conv2d_1/bias_1conv2d_2/kernel_1conv2d_2/bias_1conv2d_3/kernel_1conv2d_3/bias_1conv2d_4/kernel_1conv2d_4/bias_1conv2d_5/kernel_1conv2d_5/bias_1conv2d_6/kernel_1conv2d_6/bias_1conv2d_8/kernel_1conv2d_8/bias_1conv2d_7/kernel_1conv2d_7/bias_1dense/kernel_1dense/bias_1policy_output/kernel_1policy_output/bias_1value_output/kernel_1value_output/bias_1*$
Tin
2*
Tout
2*
_collective_manager_ids
 *:
_output_shapes(
&:���������:���������*:
_read_only_resource_inputs
	
*5
config_proto%#

CPU

GPU2*0J 8� �J *7
f2R0
.__inference_signature_wrapper___call___2234779

NoOpNoOp
�%
ConstConst"/device:CPU:0*
_output_shapes
: *
dtype0*�%
value�%B�% B�%
�
	variables
trainable_variables
non_trainable_variables
_all_variables
_misc_assets
	serve

signatures*
�
0
	1

2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23*
�
0
	1

2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23*
* 
�
 0
!1
"2
#3
$4
%5
&6
'7
(8
)9
*10
+11
,12
-13
.14
/15
016
117
218
319
420
521
622
723*
* 

8trace_0* 
"
	9serve
:serving_default* 
KE
VARIABLE_VALUEVariable_23&variables/0/.ATTRIBUTES/VARIABLE_VALUE*
KE
VARIABLE_VALUEVariable_22&variables/1/.ATTRIBUTES/VARIABLE_VALUE*
KE
VARIABLE_VALUEVariable_21&variables/2/.ATTRIBUTES/VARIABLE_VALUE*
KE
VARIABLE_VALUEVariable_20&variables/3/.ATTRIBUTES/VARIABLE_VALUE*
KE
VARIABLE_VALUEVariable_19&variables/4/.ATTRIBUTES/VARIABLE_VALUE*
KE
VARIABLE_VALUEVariable_18&variables/5/.ATTRIBUTES/VARIABLE_VALUE*
KE
VARIABLE_VALUEVariable_17&variables/6/.ATTRIBUTES/VARIABLE_VALUE*
KE
VARIABLE_VALUEVariable_16&variables/7/.ATTRIBUTES/VARIABLE_VALUE*
KE
VARIABLE_VALUEVariable_15&variables/8/.ATTRIBUTES/VARIABLE_VALUE*
KE
VARIABLE_VALUEVariable_14&variables/9/.ATTRIBUTES/VARIABLE_VALUE*
LF
VARIABLE_VALUEVariable_13'variables/10/.ATTRIBUTES/VARIABLE_VALUE*
LF
VARIABLE_VALUEVariable_12'variables/11/.ATTRIBUTES/VARIABLE_VALUE*
LF
VARIABLE_VALUEVariable_11'variables/12/.ATTRIBUTES/VARIABLE_VALUE*
LF
VARIABLE_VALUEVariable_10'variables/13/.ATTRIBUTES/VARIABLE_VALUE*
KE
VARIABLE_VALUE
Variable_9'variables/14/.ATTRIBUTES/VARIABLE_VALUE*
KE
VARIABLE_VALUE
Variable_8'variables/15/.ATTRIBUTES/VARIABLE_VALUE*
KE
VARIABLE_VALUE
Variable_7'variables/16/.ATTRIBUTES/VARIABLE_VALUE*
KE
VARIABLE_VALUE
Variable_6'variables/17/.ATTRIBUTES/VARIABLE_VALUE*
KE
VARIABLE_VALUE
Variable_5'variables/18/.ATTRIBUTES/VARIABLE_VALUE*
KE
VARIABLE_VALUE
Variable_4'variables/19/.ATTRIBUTES/VARIABLE_VALUE*
KE
VARIABLE_VALUE
Variable_3'variables/20/.ATTRIBUTES/VARIABLE_VALUE*
KE
VARIABLE_VALUE
Variable_2'variables/21/.ATTRIBUTES/VARIABLE_VALUE*
KE
VARIABLE_VALUE
Variable_1'variables/22/.ATTRIBUTES/VARIABLE_VALUE*
IC
VARIABLE_VALUEVariable'variables/23/.ATTRIBUTES/VARIABLE_VALUE*
VP
VARIABLE_VALUEconv2d_1/kernel_1+_all_variables/0/.ATTRIBUTES/VARIABLE_VALUE*
TN
VARIABLE_VALUEconv2d_3/bias_1+_all_variables/1/.ATTRIBUTES/VARIABLE_VALUE*
TN
VARIABLE_VALUEconv2d_4/bias_1+_all_variables/2/.ATTRIBUTES/VARIABLE_VALUE*
TN
VARIABLE_VALUEconv2d_8/bias_1+_all_variables/3/.ATTRIBUTES/VARIABLE_VALUE*
SM
VARIABLE_VALUEdense/kernel_1+_all_variables/4/.ATTRIBUTES/VARIABLE_VALUE*
XR
VARIABLE_VALUEvalue_output/bias_1+_all_variables/5/.ATTRIBUTES/VARIABLE_VALUE*
TN
VARIABLE_VALUEconv2d_1/bias_1+_all_variables/6/.ATTRIBUTES/VARIABLE_VALUE*
QK
VARIABLE_VALUEdense/bias_1+_all_variables/7/.ATTRIBUTES/VARIABLE_VALUE*
VP
VARIABLE_VALUEconv2d_2/kernel_1+_all_variables/8/.ATTRIBUTES/VARIABLE_VALUE*
RL
VARIABLE_VALUEconv2d/bias_1+_all_variables/9/.ATTRIBUTES/VARIABLE_VALUE*
ZT
VARIABLE_VALUEpolicy_output/bias_1,_all_variables/10/.ATTRIBUTES/VARIABLE_VALUE*
[U
VARIABLE_VALUEvalue_output/kernel_1,_all_variables/11/.ATTRIBUTES/VARIABLE_VALUE*
UO
VARIABLE_VALUEconv2d/kernel_1,_all_variables/12/.ATTRIBUTES/VARIABLE_VALUE*
UO
VARIABLE_VALUEconv2d_2/bias_1,_all_variables/13/.ATTRIBUTES/VARIABLE_VALUE*
WQ
VARIABLE_VALUEconv2d_5/kernel_1,_all_variables/14/.ATTRIBUTES/VARIABLE_VALUE*
WQ
VARIABLE_VALUEconv2d_6/kernel_1,_all_variables/15/.ATTRIBUTES/VARIABLE_VALUE*
UO
VARIABLE_VALUEconv2d_5/bias_1,_all_variables/16/.ATTRIBUTES/VARIABLE_VALUE*
UO
VARIABLE_VALUEconv2d_6/bias_1,_all_variables/17/.ATTRIBUTES/VARIABLE_VALUE*
WQ
VARIABLE_VALUEconv2d_7/kernel_1,_all_variables/18/.ATTRIBUTES/VARIABLE_VALUE*
UO
VARIABLE_VALUEconv2d_7/bias_1,_all_variables/19/.ATTRIBUTES/VARIABLE_VALUE*
\V
VARIABLE_VALUEpolicy_output/kernel_1,_all_variables/20/.ATTRIBUTES/VARIABLE_VALUE*
WQ
VARIABLE_VALUEconv2d_3/kernel_1,_all_variables/21/.ATTRIBUTES/VARIABLE_VALUE*
WQ
VARIABLE_VALUEconv2d_4/kernel_1,_all_variables/22/.ATTRIBUTES/VARIABLE_VALUE*
WQ
VARIABLE_VALUEconv2d_8/kernel_1,_all_variables/23/.ATTRIBUTES/VARIABLE_VALUE*
* 
* 
* 
O
saver_filenamePlaceholder*
_output_shapes
: *
dtype0*
shape: 
�
StatefulPartitionedCall_2StatefulPartitionedCallsaver_filenameVariable_23Variable_22Variable_21Variable_20Variable_19Variable_18Variable_17Variable_16Variable_15Variable_14Variable_13Variable_12Variable_11Variable_10
Variable_9
Variable_8
Variable_7
Variable_6
Variable_5
Variable_4
Variable_3
Variable_2
Variable_1Variableconv2d_1/kernel_1conv2d_3/bias_1conv2d_4/bias_1conv2d_8/bias_1dense/kernel_1value_output/bias_1conv2d_1/bias_1dense/bias_1conv2d_2/kernel_1conv2d/bias_1policy_output/bias_1value_output/kernel_1conv2d/kernel_1conv2d_2/bias_1conv2d_5/kernel_1conv2d_6/kernel_1conv2d_5/bias_1conv2d_6/bias_1conv2d_7/kernel_1conv2d_7/bias_1policy_output/kernel_1conv2d_3/kernel_1conv2d_4/kernel_1conv2d_8/kernel_1Const*=
Tin6
422*
Tout
2*
_collective_manager_ids
 *
_output_shapes
: * 
_read_only_resource_inputs
 *5
config_proto%#

CPU

GPU2*0J 8� �J *)
f$R"
 __inference__traced_save_2235189
�
StatefulPartitionedCall_3StatefulPartitionedCallsaver_filenameVariable_23Variable_22Variable_21Variable_20Variable_19Variable_18Variable_17Variable_16Variable_15Variable_14Variable_13Variable_12Variable_11Variable_10
Variable_9
Variable_8
Variable_7
Variable_6
Variable_5
Variable_4
Variable_3
Variable_2
Variable_1Variableconv2d_1/kernel_1conv2d_3/bias_1conv2d_4/bias_1conv2d_8/bias_1dense/kernel_1value_output/bias_1conv2d_1/bias_1dense/bias_1conv2d_2/kernel_1conv2d/bias_1policy_output/bias_1value_output/kernel_1conv2d/kernel_1conv2d_2/bias_1conv2d_5/kernel_1conv2d_6/kernel_1conv2d_5/bias_1conv2d_6/bias_1conv2d_7/kernel_1conv2d_7/bias_1policy_output/kernel_1conv2d_3/kernel_1conv2d_4/kernel_1conv2d_8/kernel_1*<
Tin5
321*
Tout
2*
_collective_manager_ids
 *
_output_shapes
: * 
_read_only_resource_inputs
 *5
config_proto%#

CPU

GPU2*0J 8� �J *,
f'R%
#__inference__traced_restore_2235342ף
�
�
.__inference_signature_wrapper___call___2234779
input_1!
unknown: 
	unknown_0: #
	unknown_1:  
	unknown_2: #
	unknown_3:  
	unknown_4: #
	unknown_5:  
	unknown_6: #
	unknown_7:  
	unknown_8: #
	unknown_9:  

unknown_10: $

unknown_11:  

unknown_12: $

unknown_13: 

unknown_14:$

unknown_15: 

unknown_16:

unknown_17:	�

unknown_18:	�

unknown_19:	�

unknown_20:

unknown_21:

unknown_22:
identity

identity_1��StatefulPartitionedCall�
StatefulPartitionedCallStatefulPartitionedCallinput_1unknown	unknown_0	unknown_1	unknown_2	unknown_3	unknown_4	unknown_5	unknown_6	unknown_7	unknown_8	unknown_9
unknown_10
unknown_11
unknown_12
unknown_13
unknown_14
unknown_15
unknown_16
unknown_17
unknown_18
unknown_19
unknown_20
unknown_21
unknown_22*$
Tin
2*
Tout
2*
_collective_manager_ids
 *:
_output_shapes(
&:���������:���������*:
_read_only_resource_inputs
	
*5
config_proto%#

CPU

GPU2*0J 8� �J *%
f R
__inference___call___2234668o
IdentityIdentity StatefulPartitionedCall:output:0^NoOp*
T0*'
_output_shapes
:���������q

Identity_1Identity StatefulPartitionedCall:output:1^NoOp*
T0*'
_output_shapes
:���������<
NoOpNoOp^StatefulPartitionedCall*
_output_shapes
 "!

identity_1Identity_1:output:0"
identityIdentity:output:0*(
_construction_contextkEagerRuntime*V
_input_shapesE
C:���������*: : : : : : : : : : : : : : : : : : : : : : : : 22
StatefulPartitionedCallStatefulPartitionedCall:'#
!
_user_specified_name	2234773:'#
!
_user_specified_name	2234771:'#
!
_user_specified_name	2234769:'#
!
_user_specified_name	2234767:'#
!
_user_specified_name	2234765:'#
!
_user_specified_name	2234763:'#
!
_user_specified_name	2234761:'#
!
_user_specified_name	2234759:'#
!
_user_specified_name	2234757:'#
!
_user_specified_name	2234755:'#
!
_user_specified_name	2234753:'#
!
_user_specified_name	2234751:'#
!
_user_specified_name	2234749:'#
!
_user_specified_name	2234747:'
#
!
_user_specified_name	2234745:'	#
!
_user_specified_name	2234743:'#
!
_user_specified_name	2234741:'#
!
_user_specified_name	2234739:'#
!
_user_specified_name	2234737:'#
!
_user_specified_name	2234735:'#
!
_user_specified_name	2234733:'#
!
_user_specified_name	2234731:'#
!
_user_specified_name	2234729:'#
!
_user_specified_name	2234727:P L
'
_output_shapes
:���������*
!
_user_specified_name	input_1
��
�+
 __inference__traced_save_2235189
file_prefix<
"read_disablecopyonread_variable_23: 2
$read_1_disablecopyonread_variable_22: >
$read_2_disablecopyonread_variable_21:  2
$read_3_disablecopyonread_variable_20: >
$read_4_disablecopyonread_variable_19:  2
$read_5_disablecopyonread_variable_18: >
$read_6_disablecopyonread_variable_17:  2
$read_7_disablecopyonread_variable_16: >
$read_8_disablecopyonread_variable_15:  2
$read_9_disablecopyonread_variable_14: ?
%read_10_disablecopyonread_variable_13:  3
%read_11_disablecopyonread_variable_12: ?
%read_12_disablecopyonread_variable_11:  3
%read_13_disablecopyonread_variable_10: >
$read_14_disablecopyonread_variable_9: 2
$read_15_disablecopyonread_variable_8:>
$read_16_disablecopyonread_variable_7: 2
$read_17_disablecopyonread_variable_6:7
$read_18_disablecopyonread_variable_5:	�3
$read_19_disablecopyonread_variable_4:	�6
$read_20_disablecopyonread_variable_3:2
$read_21_disablecopyonread_variable_2:7
$read_22_disablecopyonread_variable_1:	�0
"read_23_disablecopyonread_variable:E
+read_24_disablecopyonread_conv2d_1_kernel_1:  7
)read_25_disablecopyonread_conv2d_3_bias_1: 7
)read_26_disablecopyonread_conv2d_4_bias_1: 7
)read_27_disablecopyonread_conv2d_8_bias_1:;
(read_28_disablecopyonread_dense_kernel_1:	�;
-read_29_disablecopyonread_value_output_bias_1:7
)read_30_disablecopyonread_conv2d_1_bias_1: 5
&read_31_disablecopyonread_dense_bias_1:	�E
+read_32_disablecopyonread_conv2d_2_kernel_1:  5
'read_33_disablecopyonread_conv2d_bias_1: <
.read_34_disablecopyonread_policy_output_bias_1:A
/read_35_disablecopyonread_value_output_kernel_1:C
)read_36_disablecopyonread_conv2d_kernel_1: 7
)read_37_disablecopyonread_conv2d_2_bias_1: E
+read_38_disablecopyonread_conv2d_5_kernel_1:  E
+read_39_disablecopyonread_conv2d_6_kernel_1:  7
)read_40_disablecopyonread_conv2d_5_bias_1: 7
)read_41_disablecopyonread_conv2d_6_bias_1: E
+read_42_disablecopyonread_conv2d_7_kernel_1: 7
)read_43_disablecopyonread_conv2d_7_bias_1:C
0read_44_disablecopyonread_policy_output_kernel_1:	�E
+read_45_disablecopyonread_conv2d_3_kernel_1:  E
+read_46_disablecopyonread_conv2d_4_kernel_1:  E
+read_47_disablecopyonread_conv2d_8_kernel_1: 
savev2_const
identity_97��MergeV2Checkpoints�Read/DisableCopyOnRead�Read/ReadVariableOp�Read_1/DisableCopyOnRead�Read_1/ReadVariableOp�Read_10/DisableCopyOnRead�Read_10/ReadVariableOp�Read_11/DisableCopyOnRead�Read_11/ReadVariableOp�Read_12/DisableCopyOnRead�Read_12/ReadVariableOp�Read_13/DisableCopyOnRead�Read_13/ReadVariableOp�Read_14/DisableCopyOnRead�Read_14/ReadVariableOp�Read_15/DisableCopyOnRead�Read_15/ReadVariableOp�Read_16/DisableCopyOnRead�Read_16/ReadVariableOp�Read_17/DisableCopyOnRead�Read_17/ReadVariableOp�Read_18/DisableCopyOnRead�Read_18/ReadVariableOp�Read_19/DisableCopyOnRead�Read_19/ReadVariableOp�Read_2/DisableCopyOnRead�Read_2/ReadVariableOp�Read_20/DisableCopyOnRead�Read_20/ReadVariableOp�Read_21/DisableCopyOnRead�Read_21/ReadVariableOp�Read_22/DisableCopyOnRead�Read_22/ReadVariableOp�Read_23/DisableCopyOnRead�Read_23/ReadVariableOp�Read_24/DisableCopyOnRead�Read_24/ReadVariableOp�Read_25/DisableCopyOnRead�Read_25/ReadVariableOp�Read_26/DisableCopyOnRead�Read_26/ReadVariableOp�Read_27/DisableCopyOnRead�Read_27/ReadVariableOp�Read_28/DisableCopyOnRead�Read_28/ReadVariableOp�Read_29/DisableCopyOnRead�Read_29/ReadVariableOp�Read_3/DisableCopyOnRead�Read_3/ReadVariableOp�Read_30/DisableCopyOnRead�Read_30/ReadVariableOp�Read_31/DisableCopyOnRead�Read_31/ReadVariableOp�Read_32/DisableCopyOnRead�Read_32/ReadVariableOp�Read_33/DisableCopyOnRead�Read_33/ReadVariableOp�Read_34/DisableCopyOnRead�Read_34/ReadVariableOp�Read_35/DisableCopyOnRead�Read_35/ReadVariableOp�Read_36/DisableCopyOnRead�Read_36/ReadVariableOp�Read_37/DisableCopyOnRead�Read_37/ReadVariableOp�Read_38/DisableCopyOnRead�Read_38/ReadVariableOp�Read_39/DisableCopyOnRead�Read_39/ReadVariableOp�Read_4/DisableCopyOnRead�Read_4/ReadVariableOp�Read_40/DisableCopyOnRead�Read_40/ReadVariableOp�Read_41/DisableCopyOnRead�Read_41/ReadVariableOp�Read_42/DisableCopyOnRead�Read_42/ReadVariableOp�Read_43/DisableCopyOnRead�Read_43/ReadVariableOp�Read_44/DisableCopyOnRead�Read_44/ReadVariableOp�Read_45/DisableCopyOnRead�Read_45/ReadVariableOp�Read_46/DisableCopyOnRead�Read_46/ReadVariableOp�Read_47/DisableCopyOnRead�Read_47/ReadVariableOp�Read_5/DisableCopyOnRead�Read_5/ReadVariableOp�Read_6/DisableCopyOnRead�Read_6/ReadVariableOp�Read_7/DisableCopyOnRead�Read_7/ReadVariableOp�Read_8/DisableCopyOnRead�Read_8/ReadVariableOp�Read_9/DisableCopyOnRead�Read_9/ReadVariableOpw
StaticRegexFullMatchStaticRegexFullMatchfile_prefix"/device:CPU:**
_output_shapes
: *
pattern
^s3://.*Z
ConstConst"/device:CPU:**
_output_shapes
: *
dtype0*
valueB B.parta
Const_1Const"/device:CPU:**
_output_shapes
: *
dtype0*
valueB B
_temp/part�
SelectSelectStaticRegexFullMatch:output:0Const:output:0Const_1:output:0"/device:CPU:**
T0*
_output_shapes
: f

StringJoin
StringJoinfile_prefixSelect:output:0"/device:CPU:**
N*
_output_shapes
: e
Read/DisableCopyOnReadDisableCopyOnRead"read_disablecopyonread_variable_23*
_output_shapes
 �
Read/ReadVariableOpReadVariableOp"read_disablecopyonread_variable_23^Read/DisableCopyOnRead*&
_output_shapes
: *
dtype0b
IdentityIdentityRead/ReadVariableOp:value:0*
T0*&
_output_shapes
: i

Identity_1IdentityIdentity:output:0"/device:CPU:0*
T0*&
_output_shapes
: i
Read_1/DisableCopyOnReadDisableCopyOnRead$read_1_disablecopyonread_variable_22*
_output_shapes
 �
Read_1/ReadVariableOpReadVariableOp$read_1_disablecopyonread_variable_22^Read_1/DisableCopyOnRead*
_output_shapes
: *
dtype0Z

Identity_2IdentityRead_1/ReadVariableOp:value:0*
T0*
_output_shapes
: _

Identity_3IdentityIdentity_2:output:0"/device:CPU:0*
T0*
_output_shapes
: i
Read_2/DisableCopyOnReadDisableCopyOnRead$read_2_disablecopyonread_variable_21*
_output_shapes
 �
Read_2/ReadVariableOpReadVariableOp$read_2_disablecopyonread_variable_21^Read_2/DisableCopyOnRead*&
_output_shapes
:  *
dtype0f

Identity_4IdentityRead_2/ReadVariableOp:value:0*
T0*&
_output_shapes
:  k

Identity_5IdentityIdentity_4:output:0"/device:CPU:0*
T0*&
_output_shapes
:  i
Read_3/DisableCopyOnReadDisableCopyOnRead$read_3_disablecopyonread_variable_20*
_output_shapes
 �
Read_3/ReadVariableOpReadVariableOp$read_3_disablecopyonread_variable_20^Read_3/DisableCopyOnRead*
_output_shapes
: *
dtype0Z

Identity_6IdentityRead_3/ReadVariableOp:value:0*
T0*
_output_shapes
: _

Identity_7IdentityIdentity_6:output:0"/device:CPU:0*
T0*
_output_shapes
: i
Read_4/DisableCopyOnReadDisableCopyOnRead$read_4_disablecopyonread_variable_19*
_output_shapes
 �
Read_4/ReadVariableOpReadVariableOp$read_4_disablecopyonread_variable_19^Read_4/DisableCopyOnRead*&
_output_shapes
:  *
dtype0f

Identity_8IdentityRead_4/ReadVariableOp:value:0*
T0*&
_output_shapes
:  k

Identity_9IdentityIdentity_8:output:0"/device:CPU:0*
T0*&
_output_shapes
:  i
Read_5/DisableCopyOnReadDisableCopyOnRead$read_5_disablecopyonread_variable_18*
_output_shapes
 �
Read_5/ReadVariableOpReadVariableOp$read_5_disablecopyonread_variable_18^Read_5/DisableCopyOnRead*
_output_shapes
: *
dtype0[
Identity_10IdentityRead_5/ReadVariableOp:value:0*
T0*
_output_shapes
: a
Identity_11IdentityIdentity_10:output:0"/device:CPU:0*
T0*
_output_shapes
: i
Read_6/DisableCopyOnReadDisableCopyOnRead$read_6_disablecopyonread_variable_17*
_output_shapes
 �
Read_6/ReadVariableOpReadVariableOp$read_6_disablecopyonread_variable_17^Read_6/DisableCopyOnRead*&
_output_shapes
:  *
dtype0g
Identity_12IdentityRead_6/ReadVariableOp:value:0*
T0*&
_output_shapes
:  m
Identity_13IdentityIdentity_12:output:0"/device:CPU:0*
T0*&
_output_shapes
:  i
Read_7/DisableCopyOnReadDisableCopyOnRead$read_7_disablecopyonread_variable_16*
_output_shapes
 �
Read_7/ReadVariableOpReadVariableOp$read_7_disablecopyonread_variable_16^Read_7/DisableCopyOnRead*
_output_shapes
: *
dtype0[
Identity_14IdentityRead_7/ReadVariableOp:value:0*
T0*
_output_shapes
: a
Identity_15IdentityIdentity_14:output:0"/device:CPU:0*
T0*
_output_shapes
: i
Read_8/DisableCopyOnReadDisableCopyOnRead$read_8_disablecopyonread_variable_15*
_output_shapes
 �
Read_8/ReadVariableOpReadVariableOp$read_8_disablecopyonread_variable_15^Read_8/DisableCopyOnRead*&
_output_shapes
:  *
dtype0g
Identity_16IdentityRead_8/ReadVariableOp:value:0*
T0*&
_output_shapes
:  m
Identity_17IdentityIdentity_16:output:0"/device:CPU:0*
T0*&
_output_shapes
:  i
Read_9/DisableCopyOnReadDisableCopyOnRead$read_9_disablecopyonread_variable_14*
_output_shapes
 �
Read_9/ReadVariableOpReadVariableOp$read_9_disablecopyonread_variable_14^Read_9/DisableCopyOnRead*
_output_shapes
: *
dtype0[
Identity_18IdentityRead_9/ReadVariableOp:value:0*
T0*
_output_shapes
: a
Identity_19IdentityIdentity_18:output:0"/device:CPU:0*
T0*
_output_shapes
: k
Read_10/DisableCopyOnReadDisableCopyOnRead%read_10_disablecopyonread_variable_13*
_output_shapes
 �
Read_10/ReadVariableOpReadVariableOp%read_10_disablecopyonread_variable_13^Read_10/DisableCopyOnRead*&
_output_shapes
:  *
dtype0h
Identity_20IdentityRead_10/ReadVariableOp:value:0*
T0*&
_output_shapes
:  m
Identity_21IdentityIdentity_20:output:0"/device:CPU:0*
T0*&
_output_shapes
:  k
Read_11/DisableCopyOnReadDisableCopyOnRead%read_11_disablecopyonread_variable_12*
_output_shapes
 �
Read_11/ReadVariableOpReadVariableOp%read_11_disablecopyonread_variable_12^Read_11/DisableCopyOnRead*
_output_shapes
: *
dtype0\
Identity_22IdentityRead_11/ReadVariableOp:value:0*
T0*
_output_shapes
: a
Identity_23IdentityIdentity_22:output:0"/device:CPU:0*
T0*
_output_shapes
: k
Read_12/DisableCopyOnReadDisableCopyOnRead%read_12_disablecopyonread_variable_11*
_output_shapes
 �
Read_12/ReadVariableOpReadVariableOp%read_12_disablecopyonread_variable_11^Read_12/DisableCopyOnRead*&
_output_shapes
:  *
dtype0h
Identity_24IdentityRead_12/ReadVariableOp:value:0*
T0*&
_output_shapes
:  m
Identity_25IdentityIdentity_24:output:0"/device:CPU:0*
T0*&
_output_shapes
:  k
Read_13/DisableCopyOnReadDisableCopyOnRead%read_13_disablecopyonread_variable_10*
_output_shapes
 �
Read_13/ReadVariableOpReadVariableOp%read_13_disablecopyonread_variable_10^Read_13/DisableCopyOnRead*
_output_shapes
: *
dtype0\
Identity_26IdentityRead_13/ReadVariableOp:value:0*
T0*
_output_shapes
: a
Identity_27IdentityIdentity_26:output:0"/device:CPU:0*
T0*
_output_shapes
: j
Read_14/DisableCopyOnReadDisableCopyOnRead$read_14_disablecopyonread_variable_9*
_output_shapes
 �
Read_14/ReadVariableOpReadVariableOp$read_14_disablecopyonread_variable_9^Read_14/DisableCopyOnRead*&
_output_shapes
: *
dtype0h
Identity_28IdentityRead_14/ReadVariableOp:value:0*
T0*&
_output_shapes
: m
Identity_29IdentityIdentity_28:output:0"/device:CPU:0*
T0*&
_output_shapes
: j
Read_15/DisableCopyOnReadDisableCopyOnRead$read_15_disablecopyonread_variable_8*
_output_shapes
 �
Read_15/ReadVariableOpReadVariableOp$read_15_disablecopyonread_variable_8^Read_15/DisableCopyOnRead*
_output_shapes
:*
dtype0\
Identity_30IdentityRead_15/ReadVariableOp:value:0*
T0*
_output_shapes
:a
Identity_31IdentityIdentity_30:output:0"/device:CPU:0*
T0*
_output_shapes
:j
Read_16/DisableCopyOnReadDisableCopyOnRead$read_16_disablecopyonread_variable_7*
_output_shapes
 �
Read_16/ReadVariableOpReadVariableOp$read_16_disablecopyonread_variable_7^Read_16/DisableCopyOnRead*&
_output_shapes
: *
dtype0h
Identity_32IdentityRead_16/ReadVariableOp:value:0*
T0*&
_output_shapes
: m
Identity_33IdentityIdentity_32:output:0"/device:CPU:0*
T0*&
_output_shapes
: j
Read_17/DisableCopyOnReadDisableCopyOnRead$read_17_disablecopyonread_variable_6*
_output_shapes
 �
Read_17/ReadVariableOpReadVariableOp$read_17_disablecopyonread_variable_6^Read_17/DisableCopyOnRead*
_output_shapes
:*
dtype0\
Identity_34IdentityRead_17/ReadVariableOp:value:0*
T0*
_output_shapes
:a
Identity_35IdentityIdentity_34:output:0"/device:CPU:0*
T0*
_output_shapes
:j
Read_18/DisableCopyOnReadDisableCopyOnRead$read_18_disablecopyonread_variable_5*
_output_shapes
 �
Read_18/ReadVariableOpReadVariableOp$read_18_disablecopyonread_variable_5^Read_18/DisableCopyOnRead*
_output_shapes
:	�*
dtype0a
Identity_36IdentityRead_18/ReadVariableOp:value:0*
T0*
_output_shapes
:	�f
Identity_37IdentityIdentity_36:output:0"/device:CPU:0*
T0*
_output_shapes
:	�j
Read_19/DisableCopyOnReadDisableCopyOnRead$read_19_disablecopyonread_variable_4*
_output_shapes
 �
Read_19/ReadVariableOpReadVariableOp$read_19_disablecopyonread_variable_4^Read_19/DisableCopyOnRead*
_output_shapes	
:�*
dtype0]
Identity_38IdentityRead_19/ReadVariableOp:value:0*
T0*
_output_shapes	
:�b
Identity_39IdentityIdentity_38:output:0"/device:CPU:0*
T0*
_output_shapes	
:�j
Read_20/DisableCopyOnReadDisableCopyOnRead$read_20_disablecopyonread_variable_3*
_output_shapes
 �
Read_20/ReadVariableOpReadVariableOp$read_20_disablecopyonread_variable_3^Read_20/DisableCopyOnRead*
_output_shapes

:*
dtype0`
Identity_40IdentityRead_20/ReadVariableOp:value:0*
T0*
_output_shapes

:e
Identity_41IdentityIdentity_40:output:0"/device:CPU:0*
T0*
_output_shapes

:j
Read_21/DisableCopyOnReadDisableCopyOnRead$read_21_disablecopyonread_variable_2*
_output_shapes
 �
Read_21/ReadVariableOpReadVariableOp$read_21_disablecopyonread_variable_2^Read_21/DisableCopyOnRead*
_output_shapes
:*
dtype0\
Identity_42IdentityRead_21/ReadVariableOp:value:0*
T0*
_output_shapes
:a
Identity_43IdentityIdentity_42:output:0"/device:CPU:0*
T0*
_output_shapes
:j
Read_22/DisableCopyOnReadDisableCopyOnRead$read_22_disablecopyonread_variable_1*
_output_shapes
 �
Read_22/ReadVariableOpReadVariableOp$read_22_disablecopyonread_variable_1^Read_22/DisableCopyOnRead*
_output_shapes
:	�*
dtype0a
Identity_44IdentityRead_22/ReadVariableOp:value:0*
T0*
_output_shapes
:	�f
Identity_45IdentityIdentity_44:output:0"/device:CPU:0*
T0*
_output_shapes
:	�h
Read_23/DisableCopyOnReadDisableCopyOnRead"read_23_disablecopyonread_variable*
_output_shapes
 �
Read_23/ReadVariableOpReadVariableOp"read_23_disablecopyonread_variable^Read_23/DisableCopyOnRead*
_output_shapes
:*
dtype0\
Identity_46IdentityRead_23/ReadVariableOp:value:0*
T0*
_output_shapes
:a
Identity_47IdentityIdentity_46:output:0"/device:CPU:0*
T0*
_output_shapes
:q
Read_24/DisableCopyOnReadDisableCopyOnRead+read_24_disablecopyonread_conv2d_1_kernel_1*
_output_shapes
 �
Read_24/ReadVariableOpReadVariableOp+read_24_disablecopyonread_conv2d_1_kernel_1^Read_24/DisableCopyOnRead*&
_output_shapes
:  *
dtype0h
Identity_48IdentityRead_24/ReadVariableOp:value:0*
T0*&
_output_shapes
:  m
Identity_49IdentityIdentity_48:output:0"/device:CPU:0*
T0*&
_output_shapes
:  o
Read_25/DisableCopyOnReadDisableCopyOnRead)read_25_disablecopyonread_conv2d_3_bias_1*
_output_shapes
 �
Read_25/ReadVariableOpReadVariableOp)read_25_disablecopyonread_conv2d_3_bias_1^Read_25/DisableCopyOnRead*
_output_shapes
: *
dtype0\
Identity_50IdentityRead_25/ReadVariableOp:value:0*
T0*
_output_shapes
: a
Identity_51IdentityIdentity_50:output:0"/device:CPU:0*
T0*
_output_shapes
: o
Read_26/DisableCopyOnReadDisableCopyOnRead)read_26_disablecopyonread_conv2d_4_bias_1*
_output_shapes
 �
Read_26/ReadVariableOpReadVariableOp)read_26_disablecopyonread_conv2d_4_bias_1^Read_26/DisableCopyOnRead*
_output_shapes
: *
dtype0\
Identity_52IdentityRead_26/ReadVariableOp:value:0*
T0*
_output_shapes
: a
Identity_53IdentityIdentity_52:output:0"/device:CPU:0*
T0*
_output_shapes
: o
Read_27/DisableCopyOnReadDisableCopyOnRead)read_27_disablecopyonread_conv2d_8_bias_1*
_output_shapes
 �
Read_27/ReadVariableOpReadVariableOp)read_27_disablecopyonread_conv2d_8_bias_1^Read_27/DisableCopyOnRead*
_output_shapes
:*
dtype0\
Identity_54IdentityRead_27/ReadVariableOp:value:0*
T0*
_output_shapes
:a
Identity_55IdentityIdentity_54:output:0"/device:CPU:0*
T0*
_output_shapes
:n
Read_28/DisableCopyOnReadDisableCopyOnRead(read_28_disablecopyonread_dense_kernel_1*
_output_shapes
 �
Read_28/ReadVariableOpReadVariableOp(read_28_disablecopyonread_dense_kernel_1^Read_28/DisableCopyOnRead*
_output_shapes
:	�*
dtype0a
Identity_56IdentityRead_28/ReadVariableOp:value:0*
T0*
_output_shapes
:	�f
Identity_57IdentityIdentity_56:output:0"/device:CPU:0*
T0*
_output_shapes
:	�s
Read_29/DisableCopyOnReadDisableCopyOnRead-read_29_disablecopyonread_value_output_bias_1*
_output_shapes
 �
Read_29/ReadVariableOpReadVariableOp-read_29_disablecopyonread_value_output_bias_1^Read_29/DisableCopyOnRead*
_output_shapes
:*
dtype0\
Identity_58IdentityRead_29/ReadVariableOp:value:0*
T0*
_output_shapes
:a
Identity_59IdentityIdentity_58:output:0"/device:CPU:0*
T0*
_output_shapes
:o
Read_30/DisableCopyOnReadDisableCopyOnRead)read_30_disablecopyonread_conv2d_1_bias_1*
_output_shapes
 �
Read_30/ReadVariableOpReadVariableOp)read_30_disablecopyonread_conv2d_1_bias_1^Read_30/DisableCopyOnRead*
_output_shapes
: *
dtype0\
Identity_60IdentityRead_30/ReadVariableOp:value:0*
T0*
_output_shapes
: a
Identity_61IdentityIdentity_60:output:0"/device:CPU:0*
T0*
_output_shapes
: l
Read_31/DisableCopyOnReadDisableCopyOnRead&read_31_disablecopyonread_dense_bias_1*
_output_shapes
 �
Read_31/ReadVariableOpReadVariableOp&read_31_disablecopyonread_dense_bias_1^Read_31/DisableCopyOnRead*
_output_shapes	
:�*
dtype0]
Identity_62IdentityRead_31/ReadVariableOp:value:0*
T0*
_output_shapes	
:�b
Identity_63IdentityIdentity_62:output:0"/device:CPU:0*
T0*
_output_shapes	
:�q
Read_32/DisableCopyOnReadDisableCopyOnRead+read_32_disablecopyonread_conv2d_2_kernel_1*
_output_shapes
 �
Read_32/ReadVariableOpReadVariableOp+read_32_disablecopyonread_conv2d_2_kernel_1^Read_32/DisableCopyOnRead*&
_output_shapes
:  *
dtype0h
Identity_64IdentityRead_32/ReadVariableOp:value:0*
T0*&
_output_shapes
:  m
Identity_65IdentityIdentity_64:output:0"/device:CPU:0*
T0*&
_output_shapes
:  m
Read_33/DisableCopyOnReadDisableCopyOnRead'read_33_disablecopyonread_conv2d_bias_1*
_output_shapes
 �
Read_33/ReadVariableOpReadVariableOp'read_33_disablecopyonread_conv2d_bias_1^Read_33/DisableCopyOnRead*
_output_shapes
: *
dtype0\
Identity_66IdentityRead_33/ReadVariableOp:value:0*
T0*
_output_shapes
: a
Identity_67IdentityIdentity_66:output:0"/device:CPU:0*
T0*
_output_shapes
: t
Read_34/DisableCopyOnReadDisableCopyOnRead.read_34_disablecopyonread_policy_output_bias_1*
_output_shapes
 �
Read_34/ReadVariableOpReadVariableOp.read_34_disablecopyonread_policy_output_bias_1^Read_34/DisableCopyOnRead*
_output_shapes
:*
dtype0\
Identity_68IdentityRead_34/ReadVariableOp:value:0*
T0*
_output_shapes
:a
Identity_69IdentityIdentity_68:output:0"/device:CPU:0*
T0*
_output_shapes
:u
Read_35/DisableCopyOnReadDisableCopyOnRead/read_35_disablecopyonread_value_output_kernel_1*
_output_shapes
 �
Read_35/ReadVariableOpReadVariableOp/read_35_disablecopyonread_value_output_kernel_1^Read_35/DisableCopyOnRead*
_output_shapes

:*
dtype0`
Identity_70IdentityRead_35/ReadVariableOp:value:0*
T0*
_output_shapes

:e
Identity_71IdentityIdentity_70:output:0"/device:CPU:0*
T0*
_output_shapes

:o
Read_36/DisableCopyOnReadDisableCopyOnRead)read_36_disablecopyonread_conv2d_kernel_1*
_output_shapes
 �
Read_36/ReadVariableOpReadVariableOp)read_36_disablecopyonread_conv2d_kernel_1^Read_36/DisableCopyOnRead*&
_output_shapes
: *
dtype0h
Identity_72IdentityRead_36/ReadVariableOp:value:0*
T0*&
_output_shapes
: m
Identity_73IdentityIdentity_72:output:0"/device:CPU:0*
T0*&
_output_shapes
: o
Read_37/DisableCopyOnReadDisableCopyOnRead)read_37_disablecopyonread_conv2d_2_bias_1*
_output_shapes
 �
Read_37/ReadVariableOpReadVariableOp)read_37_disablecopyonread_conv2d_2_bias_1^Read_37/DisableCopyOnRead*
_output_shapes
: *
dtype0\
Identity_74IdentityRead_37/ReadVariableOp:value:0*
T0*
_output_shapes
: a
Identity_75IdentityIdentity_74:output:0"/device:CPU:0*
T0*
_output_shapes
: q
Read_38/DisableCopyOnReadDisableCopyOnRead+read_38_disablecopyonread_conv2d_5_kernel_1*
_output_shapes
 �
Read_38/ReadVariableOpReadVariableOp+read_38_disablecopyonread_conv2d_5_kernel_1^Read_38/DisableCopyOnRead*&
_output_shapes
:  *
dtype0h
Identity_76IdentityRead_38/ReadVariableOp:value:0*
T0*&
_output_shapes
:  m
Identity_77IdentityIdentity_76:output:0"/device:CPU:0*
T0*&
_output_shapes
:  q
Read_39/DisableCopyOnReadDisableCopyOnRead+read_39_disablecopyonread_conv2d_6_kernel_1*
_output_shapes
 �
Read_39/ReadVariableOpReadVariableOp+read_39_disablecopyonread_conv2d_6_kernel_1^Read_39/DisableCopyOnRead*&
_output_shapes
:  *
dtype0h
Identity_78IdentityRead_39/ReadVariableOp:value:0*
T0*&
_output_shapes
:  m
Identity_79IdentityIdentity_78:output:0"/device:CPU:0*
T0*&
_output_shapes
:  o
Read_40/DisableCopyOnReadDisableCopyOnRead)read_40_disablecopyonread_conv2d_5_bias_1*
_output_shapes
 �
Read_40/ReadVariableOpReadVariableOp)read_40_disablecopyonread_conv2d_5_bias_1^Read_40/DisableCopyOnRead*
_output_shapes
: *
dtype0\
Identity_80IdentityRead_40/ReadVariableOp:value:0*
T0*
_output_shapes
: a
Identity_81IdentityIdentity_80:output:0"/device:CPU:0*
T0*
_output_shapes
: o
Read_41/DisableCopyOnReadDisableCopyOnRead)read_41_disablecopyonread_conv2d_6_bias_1*
_output_shapes
 �
Read_41/ReadVariableOpReadVariableOp)read_41_disablecopyonread_conv2d_6_bias_1^Read_41/DisableCopyOnRead*
_output_shapes
: *
dtype0\
Identity_82IdentityRead_41/ReadVariableOp:value:0*
T0*
_output_shapes
: a
Identity_83IdentityIdentity_82:output:0"/device:CPU:0*
T0*
_output_shapes
: q
Read_42/DisableCopyOnReadDisableCopyOnRead+read_42_disablecopyonread_conv2d_7_kernel_1*
_output_shapes
 �
Read_42/ReadVariableOpReadVariableOp+read_42_disablecopyonread_conv2d_7_kernel_1^Read_42/DisableCopyOnRead*&
_output_shapes
: *
dtype0h
Identity_84IdentityRead_42/ReadVariableOp:value:0*
T0*&
_output_shapes
: m
Identity_85IdentityIdentity_84:output:0"/device:CPU:0*
T0*&
_output_shapes
: o
Read_43/DisableCopyOnReadDisableCopyOnRead)read_43_disablecopyonread_conv2d_7_bias_1*
_output_shapes
 �
Read_43/ReadVariableOpReadVariableOp)read_43_disablecopyonread_conv2d_7_bias_1^Read_43/DisableCopyOnRead*
_output_shapes
:*
dtype0\
Identity_86IdentityRead_43/ReadVariableOp:value:0*
T0*
_output_shapes
:a
Identity_87IdentityIdentity_86:output:0"/device:CPU:0*
T0*
_output_shapes
:v
Read_44/DisableCopyOnReadDisableCopyOnRead0read_44_disablecopyonread_policy_output_kernel_1*
_output_shapes
 �
Read_44/ReadVariableOpReadVariableOp0read_44_disablecopyonread_policy_output_kernel_1^Read_44/DisableCopyOnRead*
_output_shapes
:	�*
dtype0a
Identity_88IdentityRead_44/ReadVariableOp:value:0*
T0*
_output_shapes
:	�f
Identity_89IdentityIdentity_88:output:0"/device:CPU:0*
T0*
_output_shapes
:	�q
Read_45/DisableCopyOnReadDisableCopyOnRead+read_45_disablecopyonread_conv2d_3_kernel_1*
_output_shapes
 �
Read_45/ReadVariableOpReadVariableOp+read_45_disablecopyonread_conv2d_3_kernel_1^Read_45/DisableCopyOnRead*&
_output_shapes
:  *
dtype0h
Identity_90IdentityRead_45/ReadVariableOp:value:0*
T0*&
_output_shapes
:  m
Identity_91IdentityIdentity_90:output:0"/device:CPU:0*
T0*&
_output_shapes
:  q
Read_46/DisableCopyOnReadDisableCopyOnRead+read_46_disablecopyonread_conv2d_4_kernel_1*
_output_shapes
 �
Read_46/ReadVariableOpReadVariableOp+read_46_disablecopyonread_conv2d_4_kernel_1^Read_46/DisableCopyOnRead*&
_output_shapes
:  *
dtype0h
Identity_92IdentityRead_46/ReadVariableOp:value:0*
T0*&
_output_shapes
:  m
Identity_93IdentityIdentity_92:output:0"/device:CPU:0*
T0*&
_output_shapes
:  q
Read_47/DisableCopyOnReadDisableCopyOnRead+read_47_disablecopyonread_conv2d_8_kernel_1*
_output_shapes
 �
Read_47/ReadVariableOpReadVariableOp+read_47_disablecopyonread_conv2d_8_kernel_1^Read_47/DisableCopyOnRead*&
_output_shapes
: *
dtype0h
Identity_94IdentityRead_47/ReadVariableOp:value:0*
T0*&
_output_shapes
: m
Identity_95IdentityIdentity_94:output:0"/device:CPU:0*
T0*&
_output_shapes
: L

num_shardsConst*
_output_shapes
: *
dtype0*
value	B :f
ShardedFilename/shardConst"/device:CPU:0*
_output_shapes
: *
dtype0*
value	B : �
ShardedFilenameShardedFilenameStringJoin:output:0ShardedFilename/shard:output:0num_shards:output:0"/device:CPU:0*
_output_shapes
: �
SaveV2/tensor_namesConst"/device:CPU:0*
_output_shapes
:1*
dtype0*�
value�B�1B&variables/0/.ATTRIBUTES/VARIABLE_VALUEB&variables/1/.ATTRIBUTES/VARIABLE_VALUEB&variables/2/.ATTRIBUTES/VARIABLE_VALUEB&variables/3/.ATTRIBUTES/VARIABLE_VALUEB&variables/4/.ATTRIBUTES/VARIABLE_VALUEB&variables/5/.ATTRIBUTES/VARIABLE_VALUEB&variables/6/.ATTRIBUTES/VARIABLE_VALUEB&variables/7/.ATTRIBUTES/VARIABLE_VALUEB&variables/8/.ATTRIBUTES/VARIABLE_VALUEB&variables/9/.ATTRIBUTES/VARIABLE_VALUEB'variables/10/.ATTRIBUTES/VARIABLE_VALUEB'variables/11/.ATTRIBUTES/VARIABLE_VALUEB'variables/12/.ATTRIBUTES/VARIABLE_VALUEB'variables/13/.ATTRIBUTES/VARIABLE_VALUEB'variables/14/.ATTRIBUTES/VARIABLE_VALUEB'variables/15/.ATTRIBUTES/VARIABLE_VALUEB'variables/16/.ATTRIBUTES/VARIABLE_VALUEB'variables/17/.ATTRIBUTES/VARIABLE_VALUEB'variables/18/.ATTRIBUTES/VARIABLE_VALUEB'variables/19/.ATTRIBUTES/VARIABLE_VALUEB'variables/20/.ATTRIBUTES/VARIABLE_VALUEB'variables/21/.ATTRIBUTES/VARIABLE_VALUEB'variables/22/.ATTRIBUTES/VARIABLE_VALUEB'variables/23/.ATTRIBUTES/VARIABLE_VALUEB+_all_variables/0/.ATTRIBUTES/VARIABLE_VALUEB+_all_variables/1/.ATTRIBUTES/VARIABLE_VALUEB+_all_variables/2/.ATTRIBUTES/VARIABLE_VALUEB+_all_variables/3/.ATTRIBUTES/VARIABLE_VALUEB+_all_variables/4/.ATTRIBUTES/VARIABLE_VALUEB+_all_variables/5/.ATTRIBUTES/VARIABLE_VALUEB+_all_variables/6/.ATTRIBUTES/VARIABLE_VALUEB+_all_variables/7/.ATTRIBUTES/VARIABLE_VALUEB+_all_variables/8/.ATTRIBUTES/VARIABLE_VALUEB+_all_variables/9/.ATTRIBUTES/VARIABLE_VALUEB,_all_variables/10/.ATTRIBUTES/VARIABLE_VALUEB,_all_variables/11/.ATTRIBUTES/VARIABLE_VALUEB,_all_variables/12/.ATTRIBUTES/VARIABLE_VALUEB,_all_variables/13/.ATTRIBUTES/VARIABLE_VALUEB,_all_variables/14/.ATTRIBUTES/VARIABLE_VALUEB,_all_variables/15/.ATTRIBUTES/VARIABLE_VALUEB,_all_variables/16/.ATTRIBUTES/VARIABLE_VALUEB,_all_variables/17/.ATTRIBUTES/VARIABLE_VALUEB,_all_variables/18/.ATTRIBUTES/VARIABLE_VALUEB,_all_variables/19/.ATTRIBUTES/VARIABLE_VALUEB,_all_variables/20/.ATTRIBUTES/VARIABLE_VALUEB,_all_variables/21/.ATTRIBUTES/VARIABLE_VALUEB,_all_variables/22/.ATTRIBUTES/VARIABLE_VALUEB,_all_variables/23/.ATTRIBUTES/VARIABLE_VALUEB_CHECKPOINTABLE_OBJECT_GRAPH�
SaveV2/shape_and_slicesConst"/device:CPU:0*
_output_shapes
:1*
dtype0*u
valuelBj1B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B �

SaveV2SaveV2ShardedFilename:filename:0SaveV2/tensor_names:output:0 SaveV2/shape_and_slices:output:0Identity_1:output:0Identity_3:output:0Identity_5:output:0Identity_7:output:0Identity_9:output:0Identity_11:output:0Identity_13:output:0Identity_15:output:0Identity_17:output:0Identity_19:output:0Identity_21:output:0Identity_23:output:0Identity_25:output:0Identity_27:output:0Identity_29:output:0Identity_31:output:0Identity_33:output:0Identity_35:output:0Identity_37:output:0Identity_39:output:0Identity_41:output:0Identity_43:output:0Identity_45:output:0Identity_47:output:0Identity_49:output:0Identity_51:output:0Identity_53:output:0Identity_55:output:0Identity_57:output:0Identity_59:output:0Identity_61:output:0Identity_63:output:0Identity_65:output:0Identity_67:output:0Identity_69:output:0Identity_71:output:0Identity_73:output:0Identity_75:output:0Identity_77:output:0Identity_79:output:0Identity_81:output:0Identity_83:output:0Identity_85:output:0Identity_87:output:0Identity_89:output:0Identity_91:output:0Identity_93:output:0Identity_95:output:0savev2_const"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *?
dtypes5
321�
&MergeV2Checkpoints/checkpoint_prefixesPackShardedFilename:filename:0^SaveV2"/device:CPU:0*
N*
T0*
_output_shapes
:�
MergeV2CheckpointsMergeV2Checkpoints/MergeV2Checkpoints/checkpoint_prefixes:output:0file_prefix"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 i
Identity_96Identityfile_prefix^MergeV2Checkpoints"/device:CPU:0*
T0*
_output_shapes
: U
Identity_97IdentityIdentity_96:output:0^NoOp*
T0*
_output_shapes
: �
NoOpNoOp^MergeV2Checkpoints^Read/DisableCopyOnRead^Read/ReadVariableOp^Read_1/DisableCopyOnRead^Read_1/ReadVariableOp^Read_10/DisableCopyOnRead^Read_10/ReadVariableOp^Read_11/DisableCopyOnRead^Read_11/ReadVariableOp^Read_12/DisableCopyOnRead^Read_12/ReadVariableOp^Read_13/DisableCopyOnRead^Read_13/ReadVariableOp^Read_14/DisableCopyOnRead^Read_14/ReadVariableOp^Read_15/DisableCopyOnRead^Read_15/ReadVariableOp^Read_16/DisableCopyOnRead^Read_16/ReadVariableOp^Read_17/DisableCopyOnRead^Read_17/ReadVariableOp^Read_18/DisableCopyOnRead^Read_18/ReadVariableOp^Read_19/DisableCopyOnRead^Read_19/ReadVariableOp^Read_2/DisableCopyOnRead^Read_2/ReadVariableOp^Read_20/DisableCopyOnRead^Read_20/ReadVariableOp^Read_21/DisableCopyOnRead^Read_21/ReadVariableOp^Read_22/DisableCopyOnRead^Read_22/ReadVariableOp^Read_23/DisableCopyOnRead^Read_23/ReadVariableOp^Read_24/DisableCopyOnRead^Read_24/ReadVariableOp^Read_25/DisableCopyOnRead^Read_25/ReadVariableOp^Read_26/DisableCopyOnRead^Read_26/ReadVariableOp^Read_27/DisableCopyOnRead^Read_27/ReadVariableOp^Read_28/DisableCopyOnRead^Read_28/ReadVariableOp^Read_29/DisableCopyOnRead^Read_29/ReadVariableOp^Read_3/DisableCopyOnRead^Read_3/ReadVariableOp^Read_30/DisableCopyOnRead^Read_30/ReadVariableOp^Read_31/DisableCopyOnRead^Read_31/ReadVariableOp^Read_32/DisableCopyOnRead^Read_32/ReadVariableOp^Read_33/DisableCopyOnRead^Read_33/ReadVariableOp^Read_34/DisableCopyOnRead^Read_34/ReadVariableOp^Read_35/DisableCopyOnRead^Read_35/ReadVariableOp^Read_36/DisableCopyOnRead^Read_36/ReadVariableOp^Read_37/DisableCopyOnRead^Read_37/ReadVariableOp^Read_38/DisableCopyOnRead^Read_38/ReadVariableOp^Read_39/DisableCopyOnRead^Read_39/ReadVariableOp^Read_4/DisableCopyOnRead^Read_4/ReadVariableOp^Read_40/DisableCopyOnRead^Read_40/ReadVariableOp^Read_41/DisableCopyOnRead^Read_41/ReadVariableOp^Read_42/DisableCopyOnRead^Read_42/ReadVariableOp^Read_43/DisableCopyOnRead^Read_43/ReadVariableOp^Read_44/DisableCopyOnRead^Read_44/ReadVariableOp^Read_45/DisableCopyOnRead^Read_45/ReadVariableOp^Read_46/DisableCopyOnRead^Read_46/ReadVariableOp^Read_47/DisableCopyOnRead^Read_47/ReadVariableOp^Read_5/DisableCopyOnRead^Read_5/ReadVariableOp^Read_6/DisableCopyOnRead^Read_6/ReadVariableOp^Read_7/DisableCopyOnRead^Read_7/ReadVariableOp^Read_8/DisableCopyOnRead^Read_8/ReadVariableOp^Read_9/DisableCopyOnRead^Read_9/ReadVariableOp*
_output_shapes
 "#
identity_97Identity_97:output:0*(
_construction_contextkEagerRuntime*w
_input_shapesf
d: : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : 2(
MergeV2CheckpointsMergeV2Checkpoints20
Read/DisableCopyOnReadRead/DisableCopyOnRead2*
Read/ReadVariableOpRead/ReadVariableOp24
Read_1/DisableCopyOnReadRead_1/DisableCopyOnRead2.
Read_1/ReadVariableOpRead_1/ReadVariableOp26
Read_10/DisableCopyOnReadRead_10/DisableCopyOnRead20
Read_10/ReadVariableOpRead_10/ReadVariableOp26
Read_11/DisableCopyOnReadRead_11/DisableCopyOnRead20
Read_11/ReadVariableOpRead_11/ReadVariableOp26
Read_12/DisableCopyOnReadRead_12/DisableCopyOnRead20
Read_12/ReadVariableOpRead_12/ReadVariableOp26
Read_13/DisableCopyOnReadRead_13/DisableCopyOnRead20
Read_13/ReadVariableOpRead_13/ReadVariableOp26
Read_14/DisableCopyOnReadRead_14/DisableCopyOnRead20
Read_14/ReadVariableOpRead_14/ReadVariableOp26
Read_15/DisableCopyOnReadRead_15/DisableCopyOnRead20
Read_15/ReadVariableOpRead_15/ReadVariableOp26
Read_16/DisableCopyOnReadRead_16/DisableCopyOnRead20
Read_16/ReadVariableOpRead_16/ReadVariableOp26
Read_17/DisableCopyOnReadRead_17/DisableCopyOnRead20
Read_17/ReadVariableOpRead_17/ReadVariableOp26
Read_18/DisableCopyOnReadRead_18/DisableCopyOnRead20
Read_18/ReadVariableOpRead_18/ReadVariableOp26
Read_19/DisableCopyOnReadRead_19/DisableCopyOnRead20
Read_19/ReadVariableOpRead_19/ReadVariableOp24
Read_2/DisableCopyOnReadRead_2/DisableCopyOnRead2.
Read_2/ReadVariableOpRead_2/ReadVariableOp26
Read_20/DisableCopyOnReadRead_20/DisableCopyOnRead20
Read_20/ReadVariableOpRead_20/ReadVariableOp26
Read_21/DisableCopyOnReadRead_21/DisableCopyOnRead20
Read_21/ReadVariableOpRead_21/ReadVariableOp26
Read_22/DisableCopyOnReadRead_22/DisableCopyOnRead20
Read_22/ReadVariableOpRead_22/ReadVariableOp26
Read_23/DisableCopyOnReadRead_23/DisableCopyOnRead20
Read_23/ReadVariableOpRead_23/ReadVariableOp26
Read_24/DisableCopyOnReadRead_24/DisableCopyOnRead20
Read_24/ReadVariableOpRead_24/ReadVariableOp26
Read_25/DisableCopyOnReadRead_25/DisableCopyOnRead20
Read_25/ReadVariableOpRead_25/ReadVariableOp26
Read_26/DisableCopyOnReadRead_26/DisableCopyOnRead20
Read_26/ReadVariableOpRead_26/ReadVariableOp26
Read_27/DisableCopyOnReadRead_27/DisableCopyOnRead20
Read_27/ReadVariableOpRead_27/ReadVariableOp26
Read_28/DisableCopyOnReadRead_28/DisableCopyOnRead20
Read_28/ReadVariableOpRead_28/ReadVariableOp26
Read_29/DisableCopyOnReadRead_29/DisableCopyOnRead20
Read_29/ReadVariableOpRead_29/ReadVariableOp24
Read_3/DisableCopyOnReadRead_3/DisableCopyOnRead2.
Read_3/ReadVariableOpRead_3/ReadVariableOp26
Read_30/DisableCopyOnReadRead_30/DisableCopyOnRead20
Read_30/ReadVariableOpRead_30/ReadVariableOp26
Read_31/DisableCopyOnReadRead_31/DisableCopyOnRead20
Read_31/ReadVariableOpRead_31/ReadVariableOp26
Read_32/DisableCopyOnReadRead_32/DisableCopyOnRead20
Read_32/ReadVariableOpRead_32/ReadVariableOp26
Read_33/DisableCopyOnReadRead_33/DisableCopyOnRead20
Read_33/ReadVariableOpRead_33/ReadVariableOp26
Read_34/DisableCopyOnReadRead_34/DisableCopyOnRead20
Read_34/ReadVariableOpRead_34/ReadVariableOp26
Read_35/DisableCopyOnReadRead_35/DisableCopyOnRead20
Read_35/ReadVariableOpRead_35/ReadVariableOp26
Read_36/DisableCopyOnReadRead_36/DisableCopyOnRead20
Read_36/ReadVariableOpRead_36/ReadVariableOp26
Read_37/DisableCopyOnReadRead_37/DisableCopyOnRead20
Read_37/ReadVariableOpRead_37/ReadVariableOp26
Read_38/DisableCopyOnReadRead_38/DisableCopyOnRead20
Read_38/ReadVariableOpRead_38/ReadVariableOp26
Read_39/DisableCopyOnReadRead_39/DisableCopyOnRead20
Read_39/ReadVariableOpRead_39/ReadVariableOp24
Read_4/DisableCopyOnReadRead_4/DisableCopyOnRead2.
Read_4/ReadVariableOpRead_4/ReadVariableOp26
Read_40/DisableCopyOnReadRead_40/DisableCopyOnRead20
Read_40/ReadVariableOpRead_40/ReadVariableOp26
Read_41/DisableCopyOnReadRead_41/DisableCopyOnRead20
Read_41/ReadVariableOpRead_41/ReadVariableOp26
Read_42/DisableCopyOnReadRead_42/DisableCopyOnRead20
Read_42/ReadVariableOpRead_42/ReadVariableOp26
Read_43/DisableCopyOnReadRead_43/DisableCopyOnRead20
Read_43/ReadVariableOpRead_43/ReadVariableOp26
Read_44/DisableCopyOnReadRead_44/DisableCopyOnRead20
Read_44/ReadVariableOpRead_44/ReadVariableOp26
Read_45/DisableCopyOnReadRead_45/DisableCopyOnRead20
Read_45/ReadVariableOpRead_45/ReadVariableOp26
Read_46/DisableCopyOnReadRead_46/DisableCopyOnRead20
Read_46/ReadVariableOpRead_46/ReadVariableOp26
Read_47/DisableCopyOnReadRead_47/DisableCopyOnRead20
Read_47/ReadVariableOpRead_47/ReadVariableOp24
Read_5/DisableCopyOnReadRead_5/DisableCopyOnRead2.
Read_5/ReadVariableOpRead_5/ReadVariableOp24
Read_6/DisableCopyOnReadRead_6/DisableCopyOnRead2.
Read_6/ReadVariableOpRead_6/ReadVariableOp24
Read_7/DisableCopyOnReadRead_7/DisableCopyOnRead2.
Read_7/ReadVariableOpRead_7/ReadVariableOp24
Read_8/DisableCopyOnReadRead_8/DisableCopyOnRead2.
Read_8/ReadVariableOpRead_8/ReadVariableOp24
Read_9/DisableCopyOnReadRead_9/DisableCopyOnRead2.
Read_9/ReadVariableOpRead_9/ReadVariableOp:=19

_output_shapes
: 

_user_specified_nameConst:10-
+
_user_specified_nameconv2d_8/kernel_1:1/-
+
_user_specified_nameconv2d_4/kernel_1:1.-
+
_user_specified_nameconv2d_3/kernel_1:6-2
0
_user_specified_namepolicy_output/kernel_1:/,+
)
_user_specified_nameconv2d_7/bias_1:1+-
+
_user_specified_nameconv2d_7/kernel_1:/*+
)
_user_specified_nameconv2d_6/bias_1:/)+
)
_user_specified_nameconv2d_5/bias_1:1(-
+
_user_specified_nameconv2d_6/kernel_1:1'-
+
_user_specified_nameconv2d_5/kernel_1:/&+
)
_user_specified_nameconv2d_2/bias_1:/%+
)
_user_specified_nameconv2d/kernel_1:5$1
/
_user_specified_namevalue_output/kernel_1:4#0
.
_user_specified_namepolicy_output/bias_1:-")
'
_user_specified_nameconv2d/bias_1:1!-
+
_user_specified_nameconv2d_2/kernel_1:, (
&
_user_specified_namedense/bias_1:/+
)
_user_specified_nameconv2d_1/bias_1:3/
-
_user_specified_namevalue_output/bias_1:.*
(
_user_specified_namedense/kernel_1:/+
)
_user_specified_nameconv2d_8/bias_1:/+
)
_user_specified_nameconv2d_4/bias_1:/+
)
_user_specified_nameconv2d_3/bias_1:1-
+
_user_specified_nameconv2d_1/kernel_1:($
"
_user_specified_name
Variable:*&
$
_user_specified_name
Variable_1:*&
$
_user_specified_name
Variable_2:*&
$
_user_specified_name
Variable_3:*&
$
_user_specified_name
Variable_4:*&
$
_user_specified_name
Variable_5:*&
$
_user_specified_name
Variable_6:*&
$
_user_specified_name
Variable_7:*&
$
_user_specified_name
Variable_8:*&
$
_user_specified_name
Variable_9:+'
%
_user_specified_nameVariable_10:+'
%
_user_specified_nameVariable_11:+'
%
_user_specified_nameVariable_12:+'
%
_user_specified_nameVariable_13:+
'
%
_user_specified_nameVariable_14:+	'
%
_user_specified_nameVariable_15:+'
%
_user_specified_nameVariable_16:+'
%
_user_specified_nameVariable_17:+'
%
_user_specified_nameVariable_18:+'
%
_user_specified_nameVariable_19:+'
%
_user_specified_nameVariable_20:+'
%
_user_specified_nameVariable_21:+'
%
_user_specified_nameVariable_22:+'
%
_user_specified_nameVariable_23:C ?

_output_shapes
: 
%
_user_specified_namefile_prefix
��
�
__inference___call___2234668
input_1S
9functional_1_conv2d_1_convolution_readvariableop_resource: C
5functional_1_conv2d_1_reshape_readvariableop_resource: U
;functional_1_conv2d_1_2_convolution_readvariableop_resource:  E
7functional_1_conv2d_1_2_reshape_readvariableop_resource: U
;functional_1_conv2d_2_1_convolution_readvariableop_resource:  E
7functional_1_conv2d_2_1_reshape_readvariableop_resource: U
;functional_1_conv2d_3_1_convolution_readvariableop_resource:  E
7functional_1_conv2d_3_1_reshape_readvariableop_resource: U
;functional_1_conv2d_4_1_convolution_readvariableop_resource:  E
7functional_1_conv2d_4_1_reshape_readvariableop_resource: U
;functional_1_conv2d_5_1_convolution_readvariableop_resource:  E
7functional_1_conv2d_5_1_reshape_readvariableop_resource: U
;functional_1_conv2d_6_1_convolution_readvariableop_resource:  E
7functional_1_conv2d_6_1_reshape_readvariableop_resource: U
;functional_1_conv2d_8_1_convolution_readvariableop_resource: E
7functional_1_conv2d_8_1_reshape_readvariableop_resource:U
;functional_1_conv2d_7_1_convolution_readvariableop_resource: E
7functional_1_conv2d_7_1_reshape_readvariableop_resource:D
1functional_1_dense_1_cast_readvariableop_resource:	�C
4functional_1_dense_1_biasadd_readvariableop_resource:	�L
9functional_1_policy_output_1_cast_readvariableop_resource:	�F
8functional_1_policy_output_1_add_readvariableop_resource:J
8functional_1_value_output_1_cast_readvariableop_resource:I
;functional_1_value_output_1_biasadd_readvariableop_resource:
identity

identity_1��,functional_1/conv2d_1/Reshape/ReadVariableOp�0functional_1/conv2d_1/convolution/ReadVariableOp�.functional_1/conv2d_1_2/Reshape/ReadVariableOp�2functional_1/conv2d_1_2/convolution/ReadVariableOp�.functional_1/conv2d_2_1/Reshape/ReadVariableOp�2functional_1/conv2d_2_1/convolution/ReadVariableOp�.functional_1/conv2d_3_1/Reshape/ReadVariableOp�2functional_1/conv2d_3_1/convolution/ReadVariableOp�.functional_1/conv2d_4_1/Reshape/ReadVariableOp�2functional_1/conv2d_4_1/convolution/ReadVariableOp�.functional_1/conv2d_5_1/Reshape/ReadVariableOp�2functional_1/conv2d_5_1/convolution/ReadVariableOp�.functional_1/conv2d_6_1/Reshape/ReadVariableOp�2functional_1/conv2d_6_1/convolution/ReadVariableOp�.functional_1/conv2d_7_1/Reshape/ReadVariableOp�2functional_1/conv2d_7_1/convolution/ReadVariableOp�.functional_1/conv2d_8_1/Reshape/ReadVariableOp�2functional_1/conv2d_8_1/convolution/ReadVariableOp�+functional_1/dense_1/BiasAdd/ReadVariableOp�(functional_1/dense_1/Cast/ReadVariableOp�/functional_1/policy_output_1/Add/ReadVariableOp�0functional_1/policy_output_1/Cast/ReadVariableOp�2functional_1/value_output_1/BiasAdd/ReadVariableOp�/functional_1/value_output_1/Cast/ReadVariableOpc
functional_1/reshape_1_1/ShapeShapeinput_1*
T0*
_output_shapes
::��v
,functional_1/reshape_1_1/strided_slice/stackConst*
_output_shapes
:*
dtype0*
valueB: x
.functional_1/reshape_1_1/strided_slice/stack_1Const*
_output_shapes
:*
dtype0*
valueB:x
.functional_1/reshape_1_1/strided_slice/stack_2Const*
_output_shapes
:*
dtype0*
valueB:�
&functional_1/reshape_1_1/strided_sliceStridedSlice'functional_1/reshape_1_1/Shape:output:05functional_1/reshape_1_1/strided_slice/stack:output:07functional_1/reshape_1_1/strided_slice/stack_1:output:07functional_1/reshape_1_1/strided_slice/stack_2:output:0*
Index0*
T0*
_output_shapes
: *
shrink_axis_maskj
(functional_1/reshape_1_1/Reshape/shape/1Const*
_output_shapes
: *
dtype0*
value	B :j
(functional_1/reshape_1_1/Reshape/shape/2Const*
_output_shapes
: *
dtype0*
value	B :j
(functional_1/reshape_1_1/Reshape/shape/3Const*
_output_shapes
: *
dtype0*
value	B :�
&functional_1/reshape_1_1/Reshape/shapePack/functional_1/reshape_1_1/strided_slice:output:01functional_1/reshape_1_1/Reshape/shape/1:output:01functional_1/reshape_1_1/Reshape/shape/2:output:01functional_1/reshape_1_1/Reshape/shape/3:output:0*
N*
T0*
_output_shapes
:�
 functional_1/reshape_1_1/ReshapeReshapeinput_1/functional_1/reshape_1_1/Reshape/shape:output:0*
T0*/
_output_shapes
:����������
0functional_1/conv2d_1/convolution/ReadVariableOpReadVariableOp9functional_1_conv2d_1_convolution_readvariableop_resource*&
_output_shapes
: *
dtype0�
!functional_1/conv2d_1/convolutionConv2D)functional_1/reshape_1_1/Reshape:output:08functional_1/conv2d_1/convolution/ReadVariableOp:value:0*
T0*/
_output_shapes
:��������� *
paddingVALID*
strides
�
,functional_1/conv2d_1/Reshape/ReadVariableOpReadVariableOp5functional_1_conv2d_1_reshape_readvariableop_resource*
_output_shapes
: *
dtype0|
#functional_1/conv2d_1/Reshape/shapeConst*
_output_shapes
:*
dtype0*%
valueB"             �
functional_1/conv2d_1/ReshapeReshape4functional_1/conv2d_1/Reshape/ReadVariableOp:value:0,functional_1/conv2d_1/Reshape/shape:output:0*
T0*&
_output_shapes
: u
functional_1/conv2d_1/SqueezeSqueeze&functional_1/conv2d_1/Reshape:output:0*
T0*
_output_shapes
: �
functional_1/conv2d_1/BiasAddBiasAdd*functional_1/conv2d_1/convolution:output:0&functional_1/conv2d_1/Squeeze:output:0*
T0*/
_output_shapes
:��������� �
functional_1/conv2d_1/ReluRelu&functional_1/conv2d_1/BiasAdd:output:0*
T0*/
_output_shapes
:��������� �
2functional_1/conv2d_1_2/convolution/ReadVariableOpReadVariableOp;functional_1_conv2d_1_2_convolution_readvariableop_resource*&
_output_shapes
:  *
dtype0�
#functional_1/conv2d_1_2/convolutionConv2D(functional_1/conv2d_1/Relu:activations:0:functional_1/conv2d_1_2/convolution/ReadVariableOp:value:0*
T0*/
_output_shapes
:��������� *
paddingSAME*
strides
�
.functional_1/conv2d_1_2/Reshape/ReadVariableOpReadVariableOp7functional_1_conv2d_1_2_reshape_readvariableop_resource*
_output_shapes
: *
dtype0~
%functional_1/conv2d_1_2/Reshape/shapeConst*
_output_shapes
:*
dtype0*%
valueB"             �
functional_1/conv2d_1_2/ReshapeReshape6functional_1/conv2d_1_2/Reshape/ReadVariableOp:value:0.functional_1/conv2d_1_2/Reshape/shape:output:0*
T0*&
_output_shapes
: y
functional_1/conv2d_1_2/SqueezeSqueeze(functional_1/conv2d_1_2/Reshape:output:0*
T0*
_output_shapes
: �
functional_1/conv2d_1_2/BiasAddBiasAdd,functional_1/conv2d_1_2/convolution:output:0(functional_1/conv2d_1_2/Squeeze:output:0*
T0*/
_output_shapes
:��������� �
functional_1/conv2d_1_2/ReluRelu(functional_1/conv2d_1_2/BiasAdd:output:0*
T0*/
_output_shapes
:��������� �
2functional_1/conv2d_2_1/convolution/ReadVariableOpReadVariableOp;functional_1_conv2d_2_1_convolution_readvariableop_resource*&
_output_shapes
:  *
dtype0�
#functional_1/conv2d_2_1/convolutionConv2D*functional_1/conv2d_1_2/Relu:activations:0:functional_1/conv2d_2_1/convolution/ReadVariableOp:value:0*
T0*/
_output_shapes
:��������� *
paddingSAME*
strides
�
.functional_1/conv2d_2_1/Reshape/ReadVariableOpReadVariableOp7functional_1_conv2d_2_1_reshape_readvariableop_resource*
_output_shapes
: *
dtype0~
%functional_1/conv2d_2_1/Reshape/shapeConst*
_output_shapes
:*
dtype0*%
valueB"             �
functional_1/conv2d_2_1/ReshapeReshape6functional_1/conv2d_2_1/Reshape/ReadVariableOp:value:0.functional_1/conv2d_2_1/Reshape/shape:output:0*
T0*&
_output_shapes
: y
functional_1/conv2d_2_1/SqueezeSqueeze(functional_1/conv2d_2_1/Reshape:output:0*
T0*
_output_shapes
: �
functional_1/conv2d_2_1/BiasAddBiasAdd,functional_1/conv2d_2_1/convolution:output:0(functional_1/conv2d_2_1/Squeeze:output:0*
T0*/
_output_shapes
:��������� �
functional_1/add_1/AddAddV2(functional_1/conv2d_2_1/BiasAdd:output:0(functional_1/conv2d_1/Relu:activations:0*
T0*/
_output_shapes
:��������� w
functional_1/re_lu_1/ReluRelufunctional_1/add_1/Add:z:0*
T0*/
_output_shapes
:��������� �
2functional_1/conv2d_3_1/convolution/ReadVariableOpReadVariableOp;functional_1_conv2d_3_1_convolution_readvariableop_resource*&
_output_shapes
:  *
dtype0�
#functional_1/conv2d_3_1/convolutionConv2D'functional_1/re_lu_1/Relu:activations:0:functional_1/conv2d_3_1/convolution/ReadVariableOp:value:0*
T0*/
_output_shapes
:��������� *
paddingSAME*
strides
�
.functional_1/conv2d_3_1/Reshape/ReadVariableOpReadVariableOp7functional_1_conv2d_3_1_reshape_readvariableop_resource*
_output_shapes
: *
dtype0~
%functional_1/conv2d_3_1/Reshape/shapeConst*
_output_shapes
:*
dtype0*%
valueB"             �
functional_1/conv2d_3_1/ReshapeReshape6functional_1/conv2d_3_1/Reshape/ReadVariableOp:value:0.functional_1/conv2d_3_1/Reshape/shape:output:0*
T0*&
_output_shapes
: y
functional_1/conv2d_3_1/SqueezeSqueeze(functional_1/conv2d_3_1/Reshape:output:0*
T0*
_output_shapes
: �
functional_1/conv2d_3_1/BiasAddBiasAdd,functional_1/conv2d_3_1/convolution:output:0(functional_1/conv2d_3_1/Squeeze:output:0*
T0*/
_output_shapes
:��������� �
functional_1/conv2d_3_1/ReluRelu(functional_1/conv2d_3_1/BiasAdd:output:0*
T0*/
_output_shapes
:��������� �
2functional_1/conv2d_4_1/convolution/ReadVariableOpReadVariableOp;functional_1_conv2d_4_1_convolution_readvariableop_resource*&
_output_shapes
:  *
dtype0�
#functional_1/conv2d_4_1/convolutionConv2D*functional_1/conv2d_3_1/Relu:activations:0:functional_1/conv2d_4_1/convolution/ReadVariableOp:value:0*
T0*/
_output_shapes
:��������� *
paddingSAME*
strides
�
.functional_1/conv2d_4_1/Reshape/ReadVariableOpReadVariableOp7functional_1_conv2d_4_1_reshape_readvariableop_resource*
_output_shapes
: *
dtype0~
%functional_1/conv2d_4_1/Reshape/shapeConst*
_output_shapes
:*
dtype0*%
valueB"             �
functional_1/conv2d_4_1/ReshapeReshape6functional_1/conv2d_4_1/Reshape/ReadVariableOp:value:0.functional_1/conv2d_4_1/Reshape/shape:output:0*
T0*&
_output_shapes
: y
functional_1/conv2d_4_1/SqueezeSqueeze(functional_1/conv2d_4_1/Reshape:output:0*
T0*
_output_shapes
: �
functional_1/conv2d_4_1/BiasAddBiasAdd,functional_1/conv2d_4_1/convolution:output:0(functional_1/conv2d_4_1/Squeeze:output:0*
T0*/
_output_shapes
:��������� �
functional_1/add_1_2/AddAddV2(functional_1/conv2d_4_1/BiasAdd:output:0'functional_1/re_lu_1/Relu:activations:0*
T0*/
_output_shapes
:��������� {
functional_1/re_lu_1_2/ReluRelufunctional_1/add_1_2/Add:z:0*
T0*/
_output_shapes
:��������� �
2functional_1/conv2d_5_1/convolution/ReadVariableOpReadVariableOp;functional_1_conv2d_5_1_convolution_readvariableop_resource*&
_output_shapes
:  *
dtype0�
#functional_1/conv2d_5_1/convolutionConv2D)functional_1/re_lu_1_2/Relu:activations:0:functional_1/conv2d_5_1/convolution/ReadVariableOp:value:0*
T0*/
_output_shapes
:��������� *
paddingSAME*
strides
�
.functional_1/conv2d_5_1/Reshape/ReadVariableOpReadVariableOp7functional_1_conv2d_5_1_reshape_readvariableop_resource*
_output_shapes
: *
dtype0~
%functional_1/conv2d_5_1/Reshape/shapeConst*
_output_shapes
:*
dtype0*%
valueB"             �
functional_1/conv2d_5_1/ReshapeReshape6functional_1/conv2d_5_1/Reshape/ReadVariableOp:value:0.functional_1/conv2d_5_1/Reshape/shape:output:0*
T0*&
_output_shapes
: y
functional_1/conv2d_5_1/SqueezeSqueeze(functional_1/conv2d_5_1/Reshape:output:0*
T0*
_output_shapes
: �
functional_1/conv2d_5_1/BiasAddBiasAdd,functional_1/conv2d_5_1/convolution:output:0(functional_1/conv2d_5_1/Squeeze:output:0*
T0*/
_output_shapes
:��������� �
functional_1/conv2d_5_1/ReluRelu(functional_1/conv2d_5_1/BiasAdd:output:0*
T0*/
_output_shapes
:��������� �
2functional_1/conv2d_6_1/convolution/ReadVariableOpReadVariableOp;functional_1_conv2d_6_1_convolution_readvariableop_resource*&
_output_shapes
:  *
dtype0�
#functional_1/conv2d_6_1/convolutionConv2D*functional_1/conv2d_5_1/Relu:activations:0:functional_1/conv2d_6_1/convolution/ReadVariableOp:value:0*
T0*/
_output_shapes
:��������� *
paddingSAME*
strides
�
.functional_1/conv2d_6_1/Reshape/ReadVariableOpReadVariableOp7functional_1_conv2d_6_1_reshape_readvariableop_resource*
_output_shapes
: *
dtype0~
%functional_1/conv2d_6_1/Reshape/shapeConst*
_output_shapes
:*
dtype0*%
valueB"             �
functional_1/conv2d_6_1/ReshapeReshape6functional_1/conv2d_6_1/Reshape/ReadVariableOp:value:0.functional_1/conv2d_6_1/Reshape/shape:output:0*
T0*&
_output_shapes
: y
functional_1/conv2d_6_1/SqueezeSqueeze(functional_1/conv2d_6_1/Reshape:output:0*
T0*
_output_shapes
: �
functional_1/conv2d_6_1/BiasAddBiasAdd,functional_1/conv2d_6_1/convolution:output:0(functional_1/conv2d_6_1/Squeeze:output:0*
T0*/
_output_shapes
:��������� �
functional_1/add_2_1/AddAddV2(functional_1/conv2d_6_1/BiasAdd:output:0)functional_1/re_lu_1_2/Relu:activations:0*
T0*/
_output_shapes
:��������� {
functional_1/re_lu_2_1/ReluRelufunctional_1/add_2_1/Add:z:0*
T0*/
_output_shapes
:��������� �
2functional_1/conv2d_8_1/convolution/ReadVariableOpReadVariableOp;functional_1_conv2d_8_1_convolution_readvariableop_resource*&
_output_shapes
: *
dtype0�
#functional_1/conv2d_8_1/convolutionConv2D)functional_1/re_lu_2_1/Relu:activations:0:functional_1/conv2d_8_1/convolution/ReadVariableOp:value:0*
T0*/
_output_shapes
:���������*
paddingSAME*
strides
�
.functional_1/conv2d_8_1/Reshape/ReadVariableOpReadVariableOp7functional_1_conv2d_8_1_reshape_readvariableop_resource*
_output_shapes
:*
dtype0~
%functional_1/conv2d_8_1/Reshape/shapeConst*
_output_shapes
:*
dtype0*%
valueB"            �
functional_1/conv2d_8_1/ReshapeReshape6functional_1/conv2d_8_1/Reshape/ReadVariableOp:value:0.functional_1/conv2d_8_1/Reshape/shape:output:0*
T0*&
_output_shapes
:�
functional_1/conv2d_8_1/AddAddV2,functional_1/conv2d_8_1/convolution:output:0(functional_1/conv2d_8_1/Reshape:output:0*
T0*/
_output_shapes
:���������
functional_1/conv2d_8_1/ReluRelufunctional_1/conv2d_8_1/Add:z:0*
T0*/
_output_shapes
:���������w
&functional_1/flatten_1_1/Reshape/shapeConst*
_output_shapes
:*
dtype0*
valueB"����   �
 functional_1/flatten_1_1/ReshapeReshape*functional_1/conv2d_8_1/Relu:activations:0/functional_1/flatten_1_1/Reshape/shape:output:0*
T0*'
_output_shapes
:����������
2functional_1/conv2d_7_1/convolution/ReadVariableOpReadVariableOp;functional_1_conv2d_7_1_convolution_readvariableop_resource*&
_output_shapes
: *
dtype0�
#functional_1/conv2d_7_1/convolutionConv2D)functional_1/re_lu_2_1/Relu:activations:0:functional_1/conv2d_7_1/convolution/ReadVariableOp:value:0*
T0*/
_output_shapes
:���������*
paddingSAME*
strides
�
.functional_1/conv2d_7_1/Reshape/ReadVariableOpReadVariableOp7functional_1_conv2d_7_1_reshape_readvariableop_resource*
_output_shapes
:*
dtype0~
%functional_1/conv2d_7_1/Reshape/shapeConst*
_output_shapes
:*
dtype0*%
valueB"            �
functional_1/conv2d_7_1/ReshapeReshape6functional_1/conv2d_7_1/Reshape/ReadVariableOp:value:0.functional_1/conv2d_7_1/Reshape/shape:output:0*
T0*&
_output_shapes
:y
functional_1/conv2d_7_1/SqueezeSqueeze(functional_1/conv2d_7_1/Reshape:output:0*
T0*
_output_shapes
:�
functional_1/conv2d_7_1/BiasAddBiasAdd,functional_1/conv2d_7_1/convolution:output:0(functional_1/conv2d_7_1/Squeeze:output:0*
T0*/
_output_shapes
:����������
functional_1/conv2d_7_1/ReluRelu(functional_1/conv2d_7_1/BiasAdd:output:0*
T0*/
_output_shapes
:����������
(functional_1/dense_1/Cast/ReadVariableOpReadVariableOp1functional_1_dense_1_cast_readvariableop_resource*
_output_shapes
:	�*
dtype0�
functional_1/dense_1/MatMulMatMul)functional_1/flatten_1_1/Reshape:output:00functional_1/dense_1/Cast/ReadVariableOp:value:0*
T0*(
_output_shapes
:�����������
+functional_1/dense_1/BiasAdd/ReadVariableOpReadVariableOp4functional_1_dense_1_biasadd_readvariableop_resource*
_output_shapes	
:�*
dtype0�
functional_1/dense_1/BiasAddBiasAdd%functional_1/dense_1/MatMul:product:03functional_1/dense_1/BiasAdd/ReadVariableOp:value:0*
T0*(
_output_shapes
:����������{
functional_1/dense_1/ReluRelu%functional_1/dense_1/BiasAdd:output:0*
T0*(
_output_shapes
:����������u
$functional_1/flatten_2/Reshape/shapeConst*
_output_shapes
:*
dtype0*
valueB"����   �
functional_1/flatten_2/ReshapeReshape*functional_1/conv2d_7_1/Relu:activations:0-functional_1/flatten_2/Reshape/shape:output:0*
T0*'
_output_shapes
:����������
0functional_1/policy_output_1/Cast/ReadVariableOpReadVariableOp9functional_1_policy_output_1_cast_readvariableop_resource*
_output_shapes
:	�*
dtype0�
#functional_1/policy_output_1/MatMulMatMul'functional_1/dense_1/Relu:activations:08functional_1/policy_output_1/Cast/ReadVariableOp:value:0*
T0*'
_output_shapes
:����������
/functional_1/policy_output_1/Add/ReadVariableOpReadVariableOp8functional_1_policy_output_1_add_readvariableop_resource*
_output_shapes
:*
dtype0�
 functional_1/policy_output_1/AddAddV2-functional_1/policy_output_1/MatMul:product:07functional_1/policy_output_1/Add/ReadVariableOp:value:0*
T0*'
_output_shapes
:����������
!functional_1/policy_output_1/TanhTanh$functional_1/policy_output_1/Add:z:0*
T0*'
_output_shapes
:����������
/functional_1/value_output_1/Cast/ReadVariableOpReadVariableOp8functional_1_value_output_1_cast_readvariableop_resource*
_output_shapes

:*
dtype0�
"functional_1/value_output_1/MatMulMatMul'functional_1/flatten_2/Reshape:output:07functional_1/value_output_1/Cast/ReadVariableOp:value:0*
T0*'
_output_shapes
:����������
2functional_1/value_output_1/BiasAdd/ReadVariableOpReadVariableOp;functional_1_value_output_1_biasadd_readvariableop_resource*
_output_shapes
:*
dtype0�
#functional_1/value_output_1/BiasAddBiasAdd,functional_1/value_output_1/MatMul:product:0:functional_1/value_output_1/BiasAdd/ReadVariableOp:value:0*
T0*'
_output_shapes
:����������
#functional_1/value_output_1/SoftmaxSoftmax,functional_1/value_output_1/BiasAdd:output:0*
T0*'
_output_shapes
:���������|
IdentityIdentity-functional_1/value_output_1/Softmax:softmax:0^NoOp*
T0*'
_output_shapes
:���������v

Identity_1Identity%functional_1/policy_output_1/Tanh:y:0^NoOp*
T0*'
_output_shapes
:����������	
NoOpNoOp-^functional_1/conv2d_1/Reshape/ReadVariableOp1^functional_1/conv2d_1/convolution/ReadVariableOp/^functional_1/conv2d_1_2/Reshape/ReadVariableOp3^functional_1/conv2d_1_2/convolution/ReadVariableOp/^functional_1/conv2d_2_1/Reshape/ReadVariableOp3^functional_1/conv2d_2_1/convolution/ReadVariableOp/^functional_1/conv2d_3_1/Reshape/ReadVariableOp3^functional_1/conv2d_3_1/convolution/ReadVariableOp/^functional_1/conv2d_4_1/Reshape/ReadVariableOp3^functional_1/conv2d_4_1/convolution/ReadVariableOp/^functional_1/conv2d_5_1/Reshape/ReadVariableOp3^functional_1/conv2d_5_1/convolution/ReadVariableOp/^functional_1/conv2d_6_1/Reshape/ReadVariableOp3^functional_1/conv2d_6_1/convolution/ReadVariableOp/^functional_1/conv2d_7_1/Reshape/ReadVariableOp3^functional_1/conv2d_7_1/convolution/ReadVariableOp/^functional_1/conv2d_8_1/Reshape/ReadVariableOp3^functional_1/conv2d_8_1/convolution/ReadVariableOp,^functional_1/dense_1/BiasAdd/ReadVariableOp)^functional_1/dense_1/Cast/ReadVariableOp0^functional_1/policy_output_1/Add/ReadVariableOp1^functional_1/policy_output_1/Cast/ReadVariableOp3^functional_1/value_output_1/BiasAdd/ReadVariableOp0^functional_1/value_output_1/Cast/ReadVariableOp*
_output_shapes
 "!

identity_1Identity_1:output:0"
identityIdentity:output:0*(
_construction_contextkEagerRuntime*V
_input_shapesE
C:���������*: : : : : : : : : : : : : : : : : : : : : : : : 2\
,functional_1/conv2d_1/Reshape/ReadVariableOp,functional_1/conv2d_1/Reshape/ReadVariableOp2d
0functional_1/conv2d_1/convolution/ReadVariableOp0functional_1/conv2d_1/convolution/ReadVariableOp2`
.functional_1/conv2d_1_2/Reshape/ReadVariableOp.functional_1/conv2d_1_2/Reshape/ReadVariableOp2h
2functional_1/conv2d_1_2/convolution/ReadVariableOp2functional_1/conv2d_1_2/convolution/ReadVariableOp2`
.functional_1/conv2d_2_1/Reshape/ReadVariableOp.functional_1/conv2d_2_1/Reshape/ReadVariableOp2h
2functional_1/conv2d_2_1/convolution/ReadVariableOp2functional_1/conv2d_2_1/convolution/ReadVariableOp2`
.functional_1/conv2d_3_1/Reshape/ReadVariableOp.functional_1/conv2d_3_1/Reshape/ReadVariableOp2h
2functional_1/conv2d_3_1/convolution/ReadVariableOp2functional_1/conv2d_3_1/convolution/ReadVariableOp2`
.functional_1/conv2d_4_1/Reshape/ReadVariableOp.functional_1/conv2d_4_1/Reshape/ReadVariableOp2h
2functional_1/conv2d_4_1/convolution/ReadVariableOp2functional_1/conv2d_4_1/convolution/ReadVariableOp2`
.functional_1/conv2d_5_1/Reshape/ReadVariableOp.functional_1/conv2d_5_1/Reshape/ReadVariableOp2h
2functional_1/conv2d_5_1/convolution/ReadVariableOp2functional_1/conv2d_5_1/convolution/ReadVariableOp2`
.functional_1/conv2d_6_1/Reshape/ReadVariableOp.functional_1/conv2d_6_1/Reshape/ReadVariableOp2h
2functional_1/conv2d_6_1/convolution/ReadVariableOp2functional_1/conv2d_6_1/convolution/ReadVariableOp2`
.functional_1/conv2d_7_1/Reshape/ReadVariableOp.functional_1/conv2d_7_1/Reshape/ReadVariableOp2h
2functional_1/conv2d_7_1/convolution/ReadVariableOp2functional_1/conv2d_7_1/convolution/ReadVariableOp2`
.functional_1/conv2d_8_1/Reshape/ReadVariableOp.functional_1/conv2d_8_1/Reshape/ReadVariableOp2h
2functional_1/conv2d_8_1/convolution/ReadVariableOp2functional_1/conv2d_8_1/convolution/ReadVariableOp2Z
+functional_1/dense_1/BiasAdd/ReadVariableOp+functional_1/dense_1/BiasAdd/ReadVariableOp2T
(functional_1/dense_1/Cast/ReadVariableOp(functional_1/dense_1/Cast/ReadVariableOp2b
/functional_1/policy_output_1/Add/ReadVariableOp/functional_1/policy_output_1/Add/ReadVariableOp2d
0functional_1/policy_output_1/Cast/ReadVariableOp0functional_1/policy_output_1/Cast/ReadVariableOp2h
2functional_1/value_output_1/BiasAdd/ReadVariableOp2functional_1/value_output_1/BiasAdd/ReadVariableOp2b
/functional_1/value_output_1/Cast/ReadVariableOp/functional_1/value_output_1/Cast/ReadVariableOp:($
"
_user_specified_name
resource:($
"
_user_specified_name
resource:($
"
_user_specified_name
resource:($
"
_user_specified_name
resource:($
"
_user_specified_name
resource:($
"
_user_specified_name
resource:($
"
_user_specified_name
resource:($
"
_user_specified_name
resource:($
"
_user_specified_name
resource:($
"
_user_specified_name
resource:($
"
_user_specified_name
resource:($
"
_user_specified_name
resource:($
"
_user_specified_name
resource:($
"
_user_specified_name
resource:(
$
"
_user_specified_name
resource:(	$
"
_user_specified_name
resource:($
"
_user_specified_name
resource:($
"
_user_specified_name
resource:($
"
_user_specified_name
resource:($
"
_user_specified_name
resource:($
"
_user_specified_name
resource:($
"
_user_specified_name
resource:($
"
_user_specified_name
resource:($
"
_user_specified_name
resource:P L
'
_output_shapes
:���������*
!
_user_specified_name	input_1
�
�
.__inference_signature_wrapper___call___2234724
input_1!
unknown: 
	unknown_0: #
	unknown_1:  
	unknown_2: #
	unknown_3:  
	unknown_4: #
	unknown_5:  
	unknown_6: #
	unknown_7:  
	unknown_8: #
	unknown_9:  

unknown_10: $

unknown_11:  

unknown_12: $

unknown_13: 

unknown_14:$

unknown_15: 

unknown_16:

unknown_17:	�

unknown_18:	�

unknown_19:	�

unknown_20:

unknown_21:

unknown_22:
identity

identity_1��StatefulPartitionedCall�
StatefulPartitionedCallStatefulPartitionedCallinput_1unknown	unknown_0	unknown_1	unknown_2	unknown_3	unknown_4	unknown_5	unknown_6	unknown_7	unknown_8	unknown_9
unknown_10
unknown_11
unknown_12
unknown_13
unknown_14
unknown_15
unknown_16
unknown_17
unknown_18
unknown_19
unknown_20
unknown_21
unknown_22*$
Tin
2*
Tout
2*
_collective_manager_ids
 *:
_output_shapes(
&:���������:���������*:
_read_only_resource_inputs
	
*5
config_proto%#

CPU

GPU2*0J 8� �J *%
f R
__inference___call___2234668o
IdentityIdentity StatefulPartitionedCall:output:0^NoOp*
T0*'
_output_shapes
:���������q

Identity_1Identity StatefulPartitionedCall:output:1^NoOp*
T0*'
_output_shapes
:���������<
NoOpNoOp^StatefulPartitionedCall*
_output_shapes
 "!

identity_1Identity_1:output:0"
identityIdentity:output:0*(
_construction_contextkEagerRuntime*V
_input_shapesE
C:���������*: : : : : : : : : : : : : : : : : : : : : : : : 22
StatefulPartitionedCallStatefulPartitionedCall:'#
!
_user_specified_name	2234718:'#
!
_user_specified_name	2234716:'#
!
_user_specified_name	2234714:'#
!
_user_specified_name	2234712:'#
!
_user_specified_name	2234710:'#
!
_user_specified_name	2234708:'#
!
_user_specified_name	2234706:'#
!
_user_specified_name	2234704:'#
!
_user_specified_name	2234702:'#
!
_user_specified_name	2234700:'#
!
_user_specified_name	2234698:'#
!
_user_specified_name	2234696:'#
!
_user_specified_name	2234694:'#
!
_user_specified_name	2234692:'
#
!
_user_specified_name	2234690:'	#
!
_user_specified_name	2234688:'#
!
_user_specified_name	2234686:'#
!
_user_specified_name	2234684:'#
!
_user_specified_name	2234682:'#
!
_user_specified_name	2234680:'#
!
_user_specified_name	2234678:'#
!
_user_specified_name	2234676:'#
!
_user_specified_name	2234674:'#
!
_user_specified_name	2234672:P L
'
_output_shapes
:���������*
!
_user_specified_name	input_1
��
�
#__inference__traced_restore_2235342
file_prefix6
assignvariableop_variable_23: ,
assignvariableop_1_variable_22: 8
assignvariableop_2_variable_21:  ,
assignvariableop_3_variable_20: 8
assignvariableop_4_variable_19:  ,
assignvariableop_5_variable_18: 8
assignvariableop_6_variable_17:  ,
assignvariableop_7_variable_16: 8
assignvariableop_8_variable_15:  ,
assignvariableop_9_variable_14: 9
assignvariableop_10_variable_13:  -
assignvariableop_11_variable_12: 9
assignvariableop_12_variable_11:  -
assignvariableop_13_variable_10: 8
assignvariableop_14_variable_9: ,
assignvariableop_15_variable_8:8
assignvariableop_16_variable_7: ,
assignvariableop_17_variable_6:1
assignvariableop_18_variable_5:	�-
assignvariableop_19_variable_4:	�0
assignvariableop_20_variable_3:,
assignvariableop_21_variable_2:1
assignvariableop_22_variable_1:	�*
assignvariableop_23_variable:?
%assignvariableop_24_conv2d_1_kernel_1:  1
#assignvariableop_25_conv2d_3_bias_1: 1
#assignvariableop_26_conv2d_4_bias_1: 1
#assignvariableop_27_conv2d_8_bias_1:5
"assignvariableop_28_dense_kernel_1:	�5
'assignvariableop_29_value_output_bias_1:1
#assignvariableop_30_conv2d_1_bias_1: /
 assignvariableop_31_dense_bias_1:	�?
%assignvariableop_32_conv2d_2_kernel_1:  /
!assignvariableop_33_conv2d_bias_1: 6
(assignvariableop_34_policy_output_bias_1:;
)assignvariableop_35_value_output_kernel_1:=
#assignvariableop_36_conv2d_kernel_1: 1
#assignvariableop_37_conv2d_2_bias_1: ?
%assignvariableop_38_conv2d_5_kernel_1:  ?
%assignvariableop_39_conv2d_6_kernel_1:  1
#assignvariableop_40_conv2d_5_bias_1: 1
#assignvariableop_41_conv2d_6_bias_1: ?
%assignvariableop_42_conv2d_7_kernel_1: 1
#assignvariableop_43_conv2d_7_bias_1:=
*assignvariableop_44_policy_output_kernel_1:	�?
%assignvariableop_45_conv2d_3_kernel_1:  ?
%assignvariableop_46_conv2d_4_kernel_1:  ?
%assignvariableop_47_conv2d_8_kernel_1: 
identity_49��AssignVariableOp�AssignVariableOp_1�AssignVariableOp_10�AssignVariableOp_11�AssignVariableOp_12�AssignVariableOp_13�AssignVariableOp_14�AssignVariableOp_15�AssignVariableOp_16�AssignVariableOp_17�AssignVariableOp_18�AssignVariableOp_19�AssignVariableOp_2�AssignVariableOp_20�AssignVariableOp_21�AssignVariableOp_22�AssignVariableOp_23�AssignVariableOp_24�AssignVariableOp_25�AssignVariableOp_26�AssignVariableOp_27�AssignVariableOp_28�AssignVariableOp_29�AssignVariableOp_3�AssignVariableOp_30�AssignVariableOp_31�AssignVariableOp_32�AssignVariableOp_33�AssignVariableOp_34�AssignVariableOp_35�AssignVariableOp_36�AssignVariableOp_37�AssignVariableOp_38�AssignVariableOp_39�AssignVariableOp_4�AssignVariableOp_40�AssignVariableOp_41�AssignVariableOp_42�AssignVariableOp_43�AssignVariableOp_44�AssignVariableOp_45�AssignVariableOp_46�AssignVariableOp_47�AssignVariableOp_5�AssignVariableOp_6�AssignVariableOp_7�AssignVariableOp_8�AssignVariableOp_9�
RestoreV2/tensor_namesConst"/device:CPU:0*
_output_shapes
:1*
dtype0*�
value�B�1B&variables/0/.ATTRIBUTES/VARIABLE_VALUEB&variables/1/.ATTRIBUTES/VARIABLE_VALUEB&variables/2/.ATTRIBUTES/VARIABLE_VALUEB&variables/3/.ATTRIBUTES/VARIABLE_VALUEB&variables/4/.ATTRIBUTES/VARIABLE_VALUEB&variables/5/.ATTRIBUTES/VARIABLE_VALUEB&variables/6/.ATTRIBUTES/VARIABLE_VALUEB&variables/7/.ATTRIBUTES/VARIABLE_VALUEB&variables/8/.ATTRIBUTES/VARIABLE_VALUEB&variables/9/.ATTRIBUTES/VARIABLE_VALUEB'variables/10/.ATTRIBUTES/VARIABLE_VALUEB'variables/11/.ATTRIBUTES/VARIABLE_VALUEB'variables/12/.ATTRIBUTES/VARIABLE_VALUEB'variables/13/.ATTRIBUTES/VARIABLE_VALUEB'variables/14/.ATTRIBUTES/VARIABLE_VALUEB'variables/15/.ATTRIBUTES/VARIABLE_VALUEB'variables/16/.ATTRIBUTES/VARIABLE_VALUEB'variables/17/.ATTRIBUTES/VARIABLE_VALUEB'variables/18/.ATTRIBUTES/VARIABLE_VALUEB'variables/19/.ATTRIBUTES/VARIABLE_VALUEB'variables/20/.ATTRIBUTES/VARIABLE_VALUEB'variables/21/.ATTRIBUTES/VARIABLE_VALUEB'variables/22/.ATTRIBUTES/VARIABLE_VALUEB'variables/23/.ATTRIBUTES/VARIABLE_VALUEB+_all_variables/0/.ATTRIBUTES/VARIABLE_VALUEB+_all_variables/1/.ATTRIBUTES/VARIABLE_VALUEB+_all_variables/2/.ATTRIBUTES/VARIABLE_VALUEB+_all_variables/3/.ATTRIBUTES/VARIABLE_VALUEB+_all_variables/4/.ATTRIBUTES/VARIABLE_VALUEB+_all_variables/5/.ATTRIBUTES/VARIABLE_VALUEB+_all_variables/6/.ATTRIBUTES/VARIABLE_VALUEB+_all_variables/7/.ATTRIBUTES/VARIABLE_VALUEB+_all_variables/8/.ATTRIBUTES/VARIABLE_VALUEB+_all_variables/9/.ATTRIBUTES/VARIABLE_VALUEB,_all_variables/10/.ATTRIBUTES/VARIABLE_VALUEB,_all_variables/11/.ATTRIBUTES/VARIABLE_VALUEB,_all_variables/12/.ATTRIBUTES/VARIABLE_VALUEB,_all_variables/13/.ATTRIBUTES/VARIABLE_VALUEB,_all_variables/14/.ATTRIBUTES/VARIABLE_VALUEB,_all_variables/15/.ATTRIBUTES/VARIABLE_VALUEB,_all_variables/16/.ATTRIBUTES/VARIABLE_VALUEB,_all_variables/17/.ATTRIBUTES/VARIABLE_VALUEB,_all_variables/18/.ATTRIBUTES/VARIABLE_VALUEB,_all_variables/19/.ATTRIBUTES/VARIABLE_VALUEB,_all_variables/20/.ATTRIBUTES/VARIABLE_VALUEB,_all_variables/21/.ATTRIBUTES/VARIABLE_VALUEB,_all_variables/22/.ATTRIBUTES/VARIABLE_VALUEB,_all_variables/23/.ATTRIBUTES/VARIABLE_VALUEB_CHECKPOINTABLE_OBJECT_GRAPH�
RestoreV2/shape_and_slicesConst"/device:CPU:0*
_output_shapes
:1*
dtype0*u
valuelBj1B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B �
	RestoreV2	RestoreV2file_prefixRestoreV2/tensor_names:output:0#RestoreV2/shape_and_slices:output:0"/device:CPU:0*�
_output_shapes�
�:::::::::::::::::::::::::::::::::::::::::::::::::*?
dtypes5
321[
IdentityIdentityRestoreV2:tensors:0"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOpAssignVariableOpassignvariableop_variable_23Identity:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0]

Identity_1IdentityRestoreV2:tensors:1"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_1AssignVariableOpassignvariableop_1_variable_22Identity_1:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0]

Identity_2IdentityRestoreV2:tensors:2"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_2AssignVariableOpassignvariableop_2_variable_21Identity_2:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0]

Identity_3IdentityRestoreV2:tensors:3"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_3AssignVariableOpassignvariableop_3_variable_20Identity_3:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0]

Identity_4IdentityRestoreV2:tensors:4"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_4AssignVariableOpassignvariableop_4_variable_19Identity_4:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0]

Identity_5IdentityRestoreV2:tensors:5"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_5AssignVariableOpassignvariableop_5_variable_18Identity_5:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0]

Identity_6IdentityRestoreV2:tensors:6"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_6AssignVariableOpassignvariableop_6_variable_17Identity_6:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0]

Identity_7IdentityRestoreV2:tensors:7"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_7AssignVariableOpassignvariableop_7_variable_16Identity_7:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0]

Identity_8IdentityRestoreV2:tensors:8"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_8AssignVariableOpassignvariableop_8_variable_15Identity_8:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0]

Identity_9IdentityRestoreV2:tensors:9"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_9AssignVariableOpassignvariableop_9_variable_14Identity_9:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_10IdentityRestoreV2:tensors:10"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_10AssignVariableOpassignvariableop_10_variable_13Identity_10:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_11IdentityRestoreV2:tensors:11"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_11AssignVariableOpassignvariableop_11_variable_12Identity_11:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_12IdentityRestoreV2:tensors:12"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_12AssignVariableOpassignvariableop_12_variable_11Identity_12:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_13IdentityRestoreV2:tensors:13"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_13AssignVariableOpassignvariableop_13_variable_10Identity_13:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_14IdentityRestoreV2:tensors:14"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_14AssignVariableOpassignvariableop_14_variable_9Identity_14:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_15IdentityRestoreV2:tensors:15"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_15AssignVariableOpassignvariableop_15_variable_8Identity_15:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_16IdentityRestoreV2:tensors:16"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_16AssignVariableOpassignvariableop_16_variable_7Identity_16:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_17IdentityRestoreV2:tensors:17"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_17AssignVariableOpassignvariableop_17_variable_6Identity_17:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_18IdentityRestoreV2:tensors:18"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_18AssignVariableOpassignvariableop_18_variable_5Identity_18:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_19IdentityRestoreV2:tensors:19"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_19AssignVariableOpassignvariableop_19_variable_4Identity_19:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_20IdentityRestoreV2:tensors:20"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_20AssignVariableOpassignvariableop_20_variable_3Identity_20:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_21IdentityRestoreV2:tensors:21"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_21AssignVariableOpassignvariableop_21_variable_2Identity_21:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_22IdentityRestoreV2:tensors:22"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_22AssignVariableOpassignvariableop_22_variable_1Identity_22:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_23IdentityRestoreV2:tensors:23"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_23AssignVariableOpassignvariableop_23_variableIdentity_23:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_24IdentityRestoreV2:tensors:24"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_24AssignVariableOp%assignvariableop_24_conv2d_1_kernel_1Identity_24:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_25IdentityRestoreV2:tensors:25"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_25AssignVariableOp#assignvariableop_25_conv2d_3_bias_1Identity_25:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_26IdentityRestoreV2:tensors:26"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_26AssignVariableOp#assignvariableop_26_conv2d_4_bias_1Identity_26:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_27IdentityRestoreV2:tensors:27"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_27AssignVariableOp#assignvariableop_27_conv2d_8_bias_1Identity_27:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_28IdentityRestoreV2:tensors:28"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_28AssignVariableOp"assignvariableop_28_dense_kernel_1Identity_28:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_29IdentityRestoreV2:tensors:29"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_29AssignVariableOp'assignvariableop_29_value_output_bias_1Identity_29:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_30IdentityRestoreV2:tensors:30"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_30AssignVariableOp#assignvariableop_30_conv2d_1_bias_1Identity_30:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_31IdentityRestoreV2:tensors:31"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_31AssignVariableOp assignvariableop_31_dense_bias_1Identity_31:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_32IdentityRestoreV2:tensors:32"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_32AssignVariableOp%assignvariableop_32_conv2d_2_kernel_1Identity_32:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_33IdentityRestoreV2:tensors:33"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_33AssignVariableOp!assignvariableop_33_conv2d_bias_1Identity_33:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_34IdentityRestoreV2:tensors:34"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_34AssignVariableOp(assignvariableop_34_policy_output_bias_1Identity_34:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_35IdentityRestoreV2:tensors:35"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_35AssignVariableOp)assignvariableop_35_value_output_kernel_1Identity_35:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_36IdentityRestoreV2:tensors:36"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_36AssignVariableOp#assignvariableop_36_conv2d_kernel_1Identity_36:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_37IdentityRestoreV2:tensors:37"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_37AssignVariableOp#assignvariableop_37_conv2d_2_bias_1Identity_37:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_38IdentityRestoreV2:tensors:38"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_38AssignVariableOp%assignvariableop_38_conv2d_5_kernel_1Identity_38:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_39IdentityRestoreV2:tensors:39"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_39AssignVariableOp%assignvariableop_39_conv2d_6_kernel_1Identity_39:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_40IdentityRestoreV2:tensors:40"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_40AssignVariableOp#assignvariableop_40_conv2d_5_bias_1Identity_40:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_41IdentityRestoreV2:tensors:41"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_41AssignVariableOp#assignvariableop_41_conv2d_6_bias_1Identity_41:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_42IdentityRestoreV2:tensors:42"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_42AssignVariableOp%assignvariableop_42_conv2d_7_kernel_1Identity_42:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_43IdentityRestoreV2:tensors:43"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_43AssignVariableOp#assignvariableop_43_conv2d_7_bias_1Identity_43:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_44IdentityRestoreV2:tensors:44"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_44AssignVariableOp*assignvariableop_44_policy_output_kernel_1Identity_44:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_45IdentityRestoreV2:tensors:45"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_45AssignVariableOp%assignvariableop_45_conv2d_3_kernel_1Identity_45:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_46IdentityRestoreV2:tensors:46"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_46AssignVariableOp%assignvariableop_46_conv2d_4_kernel_1Identity_46:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0_
Identity_47IdentityRestoreV2:tensors:47"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_47AssignVariableOp%assignvariableop_47_conv2d_8_kernel_1Identity_47:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0Y
NoOpNoOp"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 �
Identity_48Identityfile_prefix^AssignVariableOp^AssignVariableOp_1^AssignVariableOp_10^AssignVariableOp_11^AssignVariableOp_12^AssignVariableOp_13^AssignVariableOp_14^AssignVariableOp_15^AssignVariableOp_16^AssignVariableOp_17^AssignVariableOp_18^AssignVariableOp_19^AssignVariableOp_2^AssignVariableOp_20^AssignVariableOp_21^AssignVariableOp_22^AssignVariableOp_23^AssignVariableOp_24^AssignVariableOp_25^AssignVariableOp_26^AssignVariableOp_27^AssignVariableOp_28^AssignVariableOp_29^AssignVariableOp_3^AssignVariableOp_30^AssignVariableOp_31^AssignVariableOp_32^AssignVariableOp_33^AssignVariableOp_34^AssignVariableOp_35^AssignVariableOp_36^AssignVariableOp_37^AssignVariableOp_38^AssignVariableOp_39^AssignVariableOp_4^AssignVariableOp_40^AssignVariableOp_41^AssignVariableOp_42^AssignVariableOp_43^AssignVariableOp_44^AssignVariableOp_45^AssignVariableOp_46^AssignVariableOp_47^AssignVariableOp_5^AssignVariableOp_6^AssignVariableOp_7^AssignVariableOp_8^AssignVariableOp_9^NoOp"/device:CPU:0*
T0*
_output_shapes
: W
Identity_49IdentityIdentity_48:output:0^NoOp_1*
T0*
_output_shapes
: �
NoOp_1NoOp^AssignVariableOp^AssignVariableOp_1^AssignVariableOp_10^AssignVariableOp_11^AssignVariableOp_12^AssignVariableOp_13^AssignVariableOp_14^AssignVariableOp_15^AssignVariableOp_16^AssignVariableOp_17^AssignVariableOp_18^AssignVariableOp_19^AssignVariableOp_2^AssignVariableOp_20^AssignVariableOp_21^AssignVariableOp_22^AssignVariableOp_23^AssignVariableOp_24^AssignVariableOp_25^AssignVariableOp_26^AssignVariableOp_27^AssignVariableOp_28^AssignVariableOp_29^AssignVariableOp_3^AssignVariableOp_30^AssignVariableOp_31^AssignVariableOp_32^AssignVariableOp_33^AssignVariableOp_34^AssignVariableOp_35^AssignVariableOp_36^AssignVariableOp_37^AssignVariableOp_38^AssignVariableOp_39^AssignVariableOp_4^AssignVariableOp_40^AssignVariableOp_41^AssignVariableOp_42^AssignVariableOp_43^AssignVariableOp_44^AssignVariableOp_45^AssignVariableOp_46^AssignVariableOp_47^AssignVariableOp_5^AssignVariableOp_6^AssignVariableOp_7^AssignVariableOp_8^AssignVariableOp_9*
_output_shapes
 "#
identity_49Identity_49:output:0*(
_construction_contextkEagerRuntime*u
_input_shapesd
b: : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : 2*
AssignVariableOp_10AssignVariableOp_102*
AssignVariableOp_11AssignVariableOp_112*
AssignVariableOp_12AssignVariableOp_122*
AssignVariableOp_13AssignVariableOp_132*
AssignVariableOp_14AssignVariableOp_142*
AssignVariableOp_15AssignVariableOp_152*
AssignVariableOp_16AssignVariableOp_162*
AssignVariableOp_17AssignVariableOp_172*
AssignVariableOp_18AssignVariableOp_182*
AssignVariableOp_19AssignVariableOp_192(
AssignVariableOp_1AssignVariableOp_12*
AssignVariableOp_20AssignVariableOp_202*
AssignVariableOp_21AssignVariableOp_212*
AssignVariableOp_22AssignVariableOp_222*
AssignVariableOp_23AssignVariableOp_232*
AssignVariableOp_24AssignVariableOp_242*
AssignVariableOp_25AssignVariableOp_252*
AssignVariableOp_26AssignVariableOp_262*
AssignVariableOp_27AssignVariableOp_272*
AssignVariableOp_28AssignVariableOp_282*
AssignVariableOp_29AssignVariableOp_292(
AssignVariableOp_2AssignVariableOp_22*
AssignVariableOp_30AssignVariableOp_302*
AssignVariableOp_31AssignVariableOp_312*
AssignVariableOp_32AssignVariableOp_322*
AssignVariableOp_33AssignVariableOp_332*
AssignVariableOp_34AssignVariableOp_342*
AssignVariableOp_35AssignVariableOp_352*
AssignVariableOp_36AssignVariableOp_362*
AssignVariableOp_37AssignVariableOp_372*
AssignVariableOp_38AssignVariableOp_382*
AssignVariableOp_39AssignVariableOp_392(
AssignVariableOp_3AssignVariableOp_32*
AssignVariableOp_40AssignVariableOp_402*
AssignVariableOp_41AssignVariableOp_412*
AssignVariableOp_42AssignVariableOp_422*
AssignVariableOp_43AssignVariableOp_432*
AssignVariableOp_44AssignVariableOp_442*
AssignVariableOp_45AssignVariableOp_452*
AssignVariableOp_46AssignVariableOp_462*
AssignVariableOp_47AssignVariableOp_472(
AssignVariableOp_4AssignVariableOp_42(
AssignVariableOp_5AssignVariableOp_52(
AssignVariableOp_6AssignVariableOp_62(
AssignVariableOp_7AssignVariableOp_72(
AssignVariableOp_8AssignVariableOp_82(
AssignVariableOp_9AssignVariableOp_92$
AssignVariableOpAssignVariableOp:10-
+
_user_specified_nameconv2d_8/kernel_1:1/-
+
_user_specified_nameconv2d_4/kernel_1:1.-
+
_user_specified_nameconv2d_3/kernel_1:6-2
0
_user_specified_namepolicy_output/kernel_1:/,+
)
_user_specified_nameconv2d_7/bias_1:1+-
+
_user_specified_nameconv2d_7/kernel_1:/*+
)
_user_specified_nameconv2d_6/bias_1:/)+
)
_user_specified_nameconv2d_5/bias_1:1(-
+
_user_specified_nameconv2d_6/kernel_1:1'-
+
_user_specified_nameconv2d_5/kernel_1:/&+
)
_user_specified_nameconv2d_2/bias_1:/%+
)
_user_specified_nameconv2d/kernel_1:5$1
/
_user_specified_namevalue_output/kernel_1:4#0
.
_user_specified_namepolicy_output/bias_1:-")
'
_user_specified_nameconv2d/bias_1:1!-
+
_user_specified_nameconv2d_2/kernel_1:, (
&
_user_specified_namedense/bias_1:/+
)
_user_specified_nameconv2d_1/bias_1:3/
-
_user_specified_namevalue_output/bias_1:.*
(
_user_specified_namedense/kernel_1:/+
)
_user_specified_nameconv2d_8/bias_1:/+
)
_user_specified_nameconv2d_4/bias_1:/+
)
_user_specified_nameconv2d_3/bias_1:1-
+
_user_specified_nameconv2d_1/kernel_1:($
"
_user_specified_name
Variable:*&
$
_user_specified_name
Variable_1:*&
$
_user_specified_name
Variable_2:*&
$
_user_specified_name
Variable_3:*&
$
_user_specified_name
Variable_4:*&
$
_user_specified_name
Variable_5:*&
$
_user_specified_name
Variable_6:*&
$
_user_specified_name
Variable_7:*&
$
_user_specified_name
Variable_8:*&
$
_user_specified_name
Variable_9:+'
%
_user_specified_nameVariable_10:+'
%
_user_specified_nameVariable_11:+'
%
_user_specified_nameVariable_12:+'
%
_user_specified_nameVariable_13:+
'
%
_user_specified_nameVariable_14:+	'
%
_user_specified_nameVariable_15:+'
%
_user_specified_nameVariable_16:+'
%
_user_specified_nameVariable_17:+'
%
_user_specified_nameVariable_18:+'
%
_user_specified_nameVariable_19:+'
%
_user_specified_nameVariable_20:+'
%
_user_specified_nameVariable_21:+'
%
_user_specified_nameVariable_22:+'
%
_user_specified_nameVariable_23:C ?

_output_shapes
: 
%
_user_specified_namefile_prefix"�L
saver_filename:0StatefulPartitionedCall_2:0StatefulPartitionedCall_38"
saved_model_main_op

NoOp*>
__saved_model_init_op%#
__saved_model_init_op

NoOp*�
serve�
1
input_1&
serve_input_1:0���������*A
policy_output0
StatefulPartitionedCall:0���������@
value_output0
StatefulPartitionedCall:1���������tensorflow/serving/predict*�
serving_default�
;
input_10
serving_default_input_1:0���������*C
policy_output2
StatefulPartitionedCall_1:0���������B
value_output2
StatefulPartitionedCall_1:1���������tensorflow/serving/predict:�!
�
	variables
trainable_variables
non_trainable_variables
_all_variables
_misc_assets
	serve

signatures"
_generic_user_object
�
0
	1

2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23"
trackable_list_wrapper
�
0
	1

2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23"
trackable_list_wrapper
 "
trackable_list_wrapper
�
 0
!1
"2
#3
$4
%5
&6
'7
(8
)9
*10
+11
,12
-13
.14
/15
016
117
218
319
420
521
622
723"
trackable_list_wrapper
 "
trackable_list_wrapper
�
8trace_02�
__inference___call___2234668�
���
FullArgSpec
args�

jargs_0
varargs
 
varkw
 
defaults
 

kwonlyargs� 
kwonlydefaults
 
annotations� *&�#
!�
input_1���������*z8trace_0
7
	9serve
:serving_default"
signature_map
':% 2conv2d/kernel
: 2conv2d/bias
):'  2conv2d_1/kernel
: 2conv2d_1/bias
):'  2conv2d_2/kernel
: 2conv2d_2/bias
):'  2conv2d_3/kernel
: 2conv2d_3/bias
):'  2conv2d_4/kernel
: 2conv2d_4/bias
):'  2conv2d_5/kernel
: 2conv2d_5/bias
):'  2conv2d_6/kernel
: 2conv2d_6/bias
):' 2conv2d_8/kernel
:2conv2d_8/bias
):' 2conv2d_7/kernel
:2conv2d_7/bias
:	�2dense/kernel
:�2
dense/bias
%:#2value_output/kernel
:2value_output/bias
':%	�2policy_output/kernel
 :2policy_output/bias
):'  2conv2d_1/kernel
: 2conv2d_3/bias
: 2conv2d_4/bias
:2conv2d_8/bias
:	�2dense/kernel
:2value_output/bias
: 2conv2d_1/bias
:�2
dense/bias
):'  2conv2d_2/kernel
: 2conv2d/bias
 :2policy_output/bias
%:#2value_output/kernel
':% 2conv2d/kernel
: 2conv2d_2/bias
):'  2conv2d_5/kernel
):'  2conv2d_6/kernel
: 2conv2d_5/bias
: 2conv2d_6/bias
):' 2conv2d_7/kernel
:2conv2d_7/bias
':%	�2policy_output/kernel
):'  2conv2d_3/kernel
):'  2conv2d_4/kernel
):' 2conv2d_8/kernel
�B�
__inference___call___2234668input_1"�
���
FullArgSpec
args�

jargs_0
varargs
 
varkw
 
defaults
 

kwonlyargs� 
kwonlydefaults
 
annotations� *
 
�B�
.__inference_signature_wrapper___call___2234724input_1"�
���
FullArgSpec
args� 
varargs
 
varkw
 
defaults
 

kwonlyargs�
	jinput_1
kwonlydefaults
 
annotations� *
 
�B�
.__inference_signature_wrapper___call___2234779input_1"�
���
FullArgSpec
args� 
varargs
 
varkw
 
defaults
 

kwonlyargs�
	jinput_1
kwonlydefaults
 
annotations� *
 �
__inference___call___2234668�	
0�-
&�#
!�
input_1���������*
� "u�r
8
policy_output'�$
policy_output���������
6
value_output&�#
value_output����������
.__inference_signature_wrapper___call___2234724�	
;�8
� 
1�.
,
input_1!�
input_1���������*"u�r
8
policy_output'�$
policy_output���������
6
value_output&�#
value_output����������
.__inference_signature_wrapper___call___2234779�	
;�8
� 
1�.
,
input_1!�
input_1���������*"u�r
8
policy_output'�$
policy_output���������
6
value_output&�#
value_output���������