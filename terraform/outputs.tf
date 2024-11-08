output "vpc_id" {
  description = "The ID of the VPC"
  value       = aws_vpc.my_vpc.id
}

output "public_subnet_id" {
  description = "The ID of the public subnet"
  value       = aws_subnet.public_subnet1.id
}

output "private_subnet_1_id" {
  description = "The ID of the first private subnet"
  value       = aws_subnet.private_subnet_1.id
}

output "private_subnet_2_id" {
  description = "The ID of the second private subnet"
  value       = aws_subnet.private_subnet_2.id
}

output "nat_gateway_id" {
  description = "The ID of the NAT Gateway"
  value       = aws_nat_gateway.nat_gw.id
}

output "private_route_table_id" {
  description = "The ID of the private route table"
  value       = aws_route_table.private_rt.id
}

output "public_route_table_id" {
  description = "The ID of the public route table"
  value       = aws_route_table.public_rt.id
}




output "cluster_endpoint" {
  description = "L'endpoint du cluster EKS"
  value       = aws_eks_cluster.my_cluster.endpoint  # Utilisation du nom correct du cluster
}

output "cluster_name" {
  description = "Le nom du cluster EKS"
  value       = aws_eks_cluster.my_cluster.name  # Utilisation du nom correct du cluster
}

output "cluster_role_arn" {
  description = "L'ARN du rôle IAM du cluster EKS"
  value       = aws_eks_cluster.my_cluster.role_arn  # Utilisation du nom correct du cluster
}
