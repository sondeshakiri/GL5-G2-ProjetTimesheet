variable "aws_region" {
  description = "La région AWS"
  type        = string
  default     = "us-east-1"  # Région mise à jour
}

variable "cluster_name" {
  description = "Nom du cluster EKS"
  type        = string
  default     = "mykubernetes"  # Nom du cluster mis à jour
}

variable "subnet_ids" {
  description = "IDs des sous-réseaux"
  type        = list(string)
  default     = ["subnet-095630f08531fac7e", "subnet-002c5cca8ae61903f"]  # Valeurs par défaut
}

variable "role_arn" {
  description = "ARN du rôle IAM pour EKS"
  type        = string
  default     = "arn:aws:iam::077108977568:role/LabRole"  # Valeur par défaut
}

variable "vpc_id" {
  description = "L'ID du VPC pour le cluster EKS"
  type        = string
  default     = "vpc-081470f81281db969"  # Remplacez par votre ID de VPC réel
}

variable "vpc_cidr" {
  description = "CIDR block for the VPC"
  type        = string
  default     = "10.0.0.0/16"  # Modifiez-le selon vos besoins
}